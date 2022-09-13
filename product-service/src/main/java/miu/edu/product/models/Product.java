package miu.edu.product.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String name;

    private String description;
    private Integer stock;

    private double price;
    public enum Category {
        Activity("Activity"),
        Automotive("Automotive"),
        Beauty("Beauty"),
        Clothing("Clothing"),
        Electronics("Electronics"),
        Entertainment("Entertainment"),
        Health("Health"),
        Home("Home"),
        Pets("Pets");
        private final String text;
        Category(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
