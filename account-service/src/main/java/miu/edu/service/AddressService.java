package miu.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.AddressDTO;
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

    public AddressDTO save(AddressDTO address) {
        return mapper.map(repository.save(mapper.map(address, Address.class)), AddressDTO.class);
    }
}
