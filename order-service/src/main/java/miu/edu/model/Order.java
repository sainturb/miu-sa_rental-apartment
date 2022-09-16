package miu.edu.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String orderNumber;
    private Instant orderDate;
    private String status;
}
