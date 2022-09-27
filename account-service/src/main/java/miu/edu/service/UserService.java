package miu.edu.service;

import miu.edu.model.Payment;
import miu.edu.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> getById(Long id);
    Optional<User> getByUsername(String username);
    User save(User user);
    Optional<User> updateInfo(User user);
    void delete(Long id);
    void updatePaymentMethod(Long id, Payment method);
    Payment getMethod(Long userId);
    Map<String, String> retrieveInfo(Long userId);
}
