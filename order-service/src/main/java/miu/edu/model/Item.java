package miu.edu.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long productId;
    private Double price;
    private Integer quantity;
    private Double total;
}
