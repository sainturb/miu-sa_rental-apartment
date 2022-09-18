package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.model.Address;
import miu.edu.model.Payment;
import miu.edu.model.User;
import miu.edu.repository.AddressRepository;
import miu.edu.repository.PaymentRepository;
import miu.edu.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final ModelMapper mapper;
    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
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
    public void updateAddress(Long id, Address address) {
        Optional<User> optional = getById(id);
        optional.ifPresent(user -> {
            if (Objects.nonNull(user.getAddress())) {
                address.setId(user.getAddress().getId());
            }
            var saved = addressRepository.save(address);
            user.setAddress(saved);
            save(user);
        });
    }

    @Override
    public Payment getMethod(Long userId) {
        Optional<User> optional = getById(userId);
        return optional.map(User::getPaymentMethod).orElse(null);
    }

    @Override
    public Address getAddress(Long userId) {
        Optional<User> optional = getById(userId);
        return optional.map(User::getAddress).orElse(null);
    }
}
