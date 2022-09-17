package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Item;
import miu.edu.model.Order;
import miu.edu.repository.ItemRepository;
import miu.edu.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final ItemRepository itemRepository;

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Optional<Order> getById(Long id) {
        return repository.findById(id);
    }

    public List<Order> getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Optional<Order> getByOrderNumber(String orderNumber) {
        return repository.findByOrderNumber(orderNumber);
    }

    public Optional<Order> getByOrderNumberAndUserId(String orderNumber, Long userId) {
        return repository.findByOrderNumberAndUserId(orderNumber, userId);
    }

    public Order save(Order order) {
        return repository.save(order);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Order placeOrder(PlaceOrderDTO placeOrder, Principal principal) {
        List<Item> items = new ArrayList<>();
        AtomicReference<Double> totalAmount = new AtomicReference<>((double) 0L);
        placeOrder.getItems().forEach(rawItem -> {
            Item item = new Item();
            item.setProductId(rawItem.getProductId());
            item.setQuantity(rawItem.getQuantity());
            item.setPrice(rawItem.getPrice());
            item.setTotal(rawItem.getQuantity() * rawItem.getPrice());
            totalAmount.updateAndGet(v -> v + item.getTotal());
            items.add(itemRepository.save(item));
        });
        Order order = new Order();
        order.setOrderDate(Instant.now());
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus("ordered");
        order.setItems(items);
        order.setTotalAmount(totalAmount.get());
        order.setUserId(Long.valueOf(principal.getName()));
        return save(order);
    }
}
