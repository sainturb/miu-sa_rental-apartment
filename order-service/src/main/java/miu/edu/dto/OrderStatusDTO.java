package miu.edu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderStatusDTO {
    @JsonProperty("orderNumber")
    private String orderNumber;
    private String status;
    private String message;
}
