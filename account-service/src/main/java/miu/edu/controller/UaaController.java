package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.model.Role;
import miu.edu.model.User;
import miu.edu.repository.RoleRepository;
import miu.edu.service.UaaServiceImpl;
import miu.edu.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/uaa")
@RequiredArgsConstructor
@CrossOrigin
public class UaaController {
    private final UaaServiceImpl service;
    private final UserServiceImpl userService;

    private final RoleRepository roleRepository;

    @PostMapping("authenticate")
    public Map<String, String> signIn(@RequestBody Map<String, String> body) {
        return service.login(body);
    }

    @PostMapping("register")
    public User register(@Valid @RequestBody User user) {
        Optional<Role> optionalRole = roleRepository.findById(1L);
        optionalRole.ifPresent(role -> {
            user.setRoles(List.of(role));
        });
        return userService.save(user);
    }

    @GetMapping("check")
    public Map<String, Boolean> validate() {
        return service.validate();
    }

    @DeleteMapping("logout")
    public void signOut(Principal principal) {
    }
}
