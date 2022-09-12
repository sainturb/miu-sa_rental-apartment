package miu.edu.shipment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.shipment.models.Address;
import miu.edu.shipment.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
    private final AddressRepository repository;

    public Address save(Address address) {
        return repository.save(address);
    }

    public Map<String, String> ship(Long userId) {
        Optional<Address> address = repository.findByUserId(userId);
        if (address.isPresent()) {
            log.info("Shipped to " + address.get().getAddress());
            return Map.of("response", "Shipped to " + address.get().getAddress());
        } else {
            log.info("No address is set");
            return Map.of("response", "No address is set");
        }
    }
}
