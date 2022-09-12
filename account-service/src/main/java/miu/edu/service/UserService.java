package miu.edu.service;

import miu.edu.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAll();
    Optional<UserDTO> getById(Long id);
    Optional<UserDTO> getByUsername(String username);
    UserDTO save(UserDTO user);
    void delete(Long id);
    void updatePaymentMethod(Long id, String method);
}
