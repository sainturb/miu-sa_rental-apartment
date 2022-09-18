package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.model.User;
import miu.edu.service.UaaServiceImpl;
import miu.edu.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("api/uaa")
@RequiredArgsConstructor
@CrossOrigin
public class UaaController {
    private final UaaServiceImpl service;
    private final UserServiceImpl userService;

    @PostMapping("authenticate")
    public Map<String, String> signIn(@RequestBody Map<String, String> body) {
        return service.login(body);
    }

    @PostMapping("register")
    public User register(@Valid @RequestBody User user) {
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
