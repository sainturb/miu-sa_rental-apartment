package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Order;
import miu.edu.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

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
        Order order = new Order();
        order.setStartDate(placeOrder.getItem().getStartDate());
        order.setEndDate(placeOrder.getItem().getEndDate());
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus("initial");
        order.setProductId(placeOrder.getItem().getProductId());
        order.setTotalAmount(placeOrder.getItem().getPrice());
        order.setUserId(Long.valueOf(principal.getName()));
        return save(order);
    }
}
