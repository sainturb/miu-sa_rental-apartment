package miu.edu.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NonNull
    private String address;
    private String address1;
    @NonNull
    private String state;
    @NonNull
    private String street;
    @NonNull
    private String zipcode;
    @OneToOne
    private User user;
}
