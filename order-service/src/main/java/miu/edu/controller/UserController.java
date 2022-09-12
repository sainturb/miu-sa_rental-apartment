package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.UserDTO;
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
    public List<UserDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public UserDTO save(@RequestBody UserDTO user) {
        return service.save(user);
    }

    @PutMapping("{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO user) {
        user.setId(id);
        return service.save(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
