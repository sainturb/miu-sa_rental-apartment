package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.model.User;
import miu.edu.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserServiceImpl service;

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return service.save(user);
    }

    @PutMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return service.save(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
