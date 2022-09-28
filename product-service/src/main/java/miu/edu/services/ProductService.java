package miu.edu.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.client.AccountClient;
import miu.edu.dto.NotificationDTO;
import miu.edu.models.BetweenDateDTO;
import miu.edu.models.Product;
import miu.edu.models.Review;
import miu.edu.repositories.ProductRepository;
import miu.edu.search.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductSearchRepository searchRepository;

    private final KafkaTemplate<String, NotificationDTO> kafkaTemplate;

    private final AccountClient accountClient;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public long getCount() {
        return repository.count();
    }

    public Optional<Product> getById(Long id) {
        return repository.findById(id);
    }

    public Product save(Product product) {
        searchRepository.save(product);
        return repository.save(product);
    }

    public void delete(Long id) {
        searchRepository.deleteById(id);
        repository.deleteById(id);
    }

    public void makeUnavailableBetween(Long id, BetweenDateDTO between) {
        Optional<Product> productOptional = repository.findById(id);
        productOptional.ifPresent(product -> {
            product.setAvailableFrom(between.getEndDate().plusDays(1));
            var saved = save(product);
            if (DAYS.between(saved.getAvailableFrom(), saved.getAvailableUntil()) < 10) {
                sendWarningNotification(saved);
            }
            sendOrderedNotification(saved, between.getStartDate(), between.getEndDate());
            log.info("{} between these day it will be available: from {} - until {}", saved.getAddress(), saved.getAvailableFrom(), saved.getAvailableUntil());
        });
    }

    private void sendOrderedNotification(Product product, LocalDate start, LocalDate end) {
        try {
            Map<String, String> info = accountClient.retrieveInfo(product.getOwnerId());
            NotificationDTO notification = new NotificationDTO();
            notification.setSubject("Product ordered from your rental");
            notification.setText(String.format("%s where located %s is reserved by user during %s - %s",
                    product.getHomeType(),
                    product.getAddress(),
                    start,
                    end
            ));
            notification.setTo(Objects.nonNull(info.get("email")) ? info.get("email") : "empty");
            kafkaTemplate.send("notification.events", notification);
        } catch (FeignException e) {
            e.printStackTrace();
        }
    }

    private void sendWarningNotification(Product product) {
       try {
           Map<String, String> info = accountClient.retrieveInfo(product.getOwnerId());
           NotificationDTO notification = new NotificationDTO();
           notification.setSubject("Warning availability");
           notification.setText(String.format("%s where located %s is only available for %s days. \nTake action if you want to extend the periods.",
                   product.getHomeType(),
                   product.getAddress(),
                   DAYS.between(product.getAvailableUntil(), product.getAvailableFrom())
           ));
           notification.setTo(Objects.nonNull(info.get("email")) ? info.get("email") : "empty");
           kafkaTemplate.send("notification.events", notification);
       } catch (FeignException e) {
           e.printStackTrace();
       }
    }

    public Map<String, Object> getAvailability(Long id, BetweenDateDTO between) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            var available = !optional.get().getAvailableFrom().isAfter(between.getStartDate()) &&
                    !optional.get().getAvailableUntil().isBefore(between.getEndDate());
            return Map.of("available", available, "from", optional.get().getAvailableFrom(), "until", optional.get().getAvailableUntil(), "price", optional.get().getPrice());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    public void updateRating(Long productId, List<Review> reviews) {
        var count = 1 + reviews.size();
        AtomicReference<Double> total = new AtomicReference<>((double) 5);
        this.getById(productId)
                .ifPresent(product -> {
                    reviews.stream()
                            .map(Review::getRating)
                            .reduce(Double::sum)
                            .ifPresent(sum -> total.getAndUpdate(current -> current + sum));
                    product.setRating(total.get() / count);
                    this.save(product);
                });
    }
}
