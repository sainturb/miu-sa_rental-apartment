package miu.edu.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String orderNumber;
    private Instant orderDate;
    private String status;
    private Double totalAmount;
    private Long userId;
    private String reason;
    @OneToMany
    @JoinColumn(name = "id_order")
    private List<Item> items;
}
