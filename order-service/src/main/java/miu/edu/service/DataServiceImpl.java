package miu.edu.service;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.DataDTO;
import miu.edu.model.Data;
import miu.edu.repository.DataRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataRepository repository;
    private final ModelMapper mapper;
    @Override
    public List<DataDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(u -> mapper.map(u, DataDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DataDTO> getById(Long id) {
        return repository.findById(id)
                .map(u -> mapper.map(u, DataDTO.class));
    }

    @Override
    public DataDTO save(DataDTO user) {
        return mapper.map(repository.save(mapper.map(user, Data.class)), DataDTO.class);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
