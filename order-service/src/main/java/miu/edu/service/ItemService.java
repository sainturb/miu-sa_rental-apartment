package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.model.Item;
import miu.edu.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;
    public List<Item> getAll() {
        return repository.findAll();
    }

    public Optional<Item> getById(Long id) {
        return repository.findById(id);
    }

    public Item save(Item item) {
        return repository.save(item);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
