package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.DataDTO;
import miu.edu.service.DataServiceImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/data")
@RequiredArgsConstructor
@CrossOrigin
public class DataController {
    private final DataServiceImpl service;

    private final JobLauncher jobLauncher;

    private final Job importData;

    @GetMapping("batch")
    public String startBatch() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        jobLauncher.run(importData,new JobParameters(new HashMap<>()));
        return "successfully run batch";
    }


    @GetMapping
    public List<DataDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<DataDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public DataDTO save(@RequestBody DataDTO data) {
        return service.save(data);
    }

    @PutMapping("{id}")
    public DataDTO update(@PathVariable Long id, @RequestBody DataDTO data) {
        data.setId(id);
        return service.save(data);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
