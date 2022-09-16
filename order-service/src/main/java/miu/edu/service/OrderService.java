package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.model.Order;
import miu.edu.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Order save(Order order) {
        return repository.save(order);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
