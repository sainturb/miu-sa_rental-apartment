package miu.edu.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BetweenDateDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
