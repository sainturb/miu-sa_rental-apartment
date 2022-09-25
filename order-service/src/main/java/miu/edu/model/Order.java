package miu.edu.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Double totalAmount;
    private Long userId;
    private String note;
    private Long productId;
}
