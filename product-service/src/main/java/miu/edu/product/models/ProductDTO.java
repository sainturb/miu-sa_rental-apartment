package miu.edu.product.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String category;
    private String name;
    private String description;
    private Integer stock;
    private double price;
}
