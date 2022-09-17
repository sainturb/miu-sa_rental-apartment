package miu.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.model.Address;
import miu.edu.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
    private final AddressRepository repository;
    private final ModelMapper mapper;

    public Address save(Address address) {
        return repository.save(address);
    }
}
