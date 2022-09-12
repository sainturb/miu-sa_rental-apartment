package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.UserDTO;
import miu.edu.model.User;
import miu.edu.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper mapper;
    @Override
    public List<UserDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(u -> mapper.map(u, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getById(Long id) {
        return repository.findById(id)
                .map(u -> mapper.map(u, UserDTO.class));
    }

    @Override
    public UserDTO save(UserDTO user) {
        return mapper.map(repository.save(mapper.map(user, User.class)), UserDTO.class);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
