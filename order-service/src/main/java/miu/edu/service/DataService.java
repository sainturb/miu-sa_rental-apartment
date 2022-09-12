package miu.edu.service;

import miu.edu.dto.DataDTO;

import java.util.List;
import java.util.Optional;

public interface DataService {
    List<DataDTO> getAll();
    Optional<DataDTO> getById(Long id);
    DataDTO save(DataDTO user);
    void delete(Long id);
}
