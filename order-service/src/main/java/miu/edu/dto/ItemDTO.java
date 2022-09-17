package miu.edu.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ItemDTO {
    @NonNull
    private Long productId;
    @NonNull
    private Double price;
    @NonNull
    private Integer quantity;
}
