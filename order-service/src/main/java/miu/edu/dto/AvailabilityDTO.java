package miu.edu.dto;

import lombok.Data;

@Data
public class AvailabilityDTO {
    private boolean available = false;
    private int current = 0;
}
