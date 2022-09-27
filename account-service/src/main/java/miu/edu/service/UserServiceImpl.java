package miu.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.model.Payment;
import miu.edu.model.User;
import miu.edu.repository.PaymentRepository;
import miu.edu.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PaymentRepository paymentRepository;

    private final ModelMapper mapper;
    private final RedisService redisService;
    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        if (redisService.isExist(id.toString())) {
            return Optional.of(redisService.getValue(id.toString()));
        }
        log.info("Data from DB");
        Optional<User> optionalUser = repository.findById(id);
        optionalUser.ifPresent(user -> redisService.setValue(user.getId().toString(), user));
        return optionalUser;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        User saved = repository.save(user);
        redisService.setValue(user.getId().toString(), saved);
        return saved;
    }

    @Override
    public void delete(Long id) {
        redisService.deleteByKey(id.toString());
        repository.deleteById(id);
    }

    @Override
    public void updatePaymentMethod(Long id, Payment method) {
        Optional<User> optional = getById(id);
        optional.ifPresent(user -> {
            if (Objects.nonNull(user.getPaymentMethod())) {
                method.setId(user.getPaymentMethod().getId());
            }
            var saved = paymentRepository.save(method);
            user.setPaymentMethod(saved);
            save(user);
        });
    }

    @Override
    public Payment getMethod(Long userId) {
        Optional<User> optional = getById(userId);
        return optional.map(User::getPaymentMethod).orElse(null);
    }

    @Override
    public Map<String, String> retrieveInfo(Long userId) {
        Map<String, String> map = new HashMap<>();
        Optional<User> optional = getById(userId);
        optional.ifPresent(user -> {
            map.put("email", user.getEmail());
            map.put("fullname", String.format("%s %s", user.getFirstname(), user.getLastname()));
            map.put("username", user.getUsername());
        });
        return map;
    }

    public Optional<User> updateInfo(User user) {
        Optional<User> optional = getById(user.getId());
        return optional.map(updatingUser -> {
            updatingUser.setEmail(user.getEmail());
            updatingUser.setFirstname(user.getFirstname());
            updatingUser.setLastname(user.getLastname());
            return save(updatingUser);
        });
    }
}
