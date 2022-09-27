package miu.edu.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailabilityDTO {
    private boolean available = false;
    private double price;
    private LocalDate from;
    private LocalDate until;
}
