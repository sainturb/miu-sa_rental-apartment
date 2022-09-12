package miu.edu.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String address;
    private String address1;
    private String state;
    private String street;
    private String zipcode;
    private Long userId;
}
