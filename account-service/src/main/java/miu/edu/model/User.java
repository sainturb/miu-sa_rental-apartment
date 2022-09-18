package miu.edu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;

    @OneToOne
    private Address address;

    @OneToOne
    private Payment paymentMethod;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<Role> roles;

    public Map<String, Object> toMap() {
        return Map.of(
                "id", this.id,
                "fullname", this.firstname + " " + this.lastname,
                "username", this.username,
                "email", this.email,
                "roles", this.roles.stream().map(Role::getRole).collect(Collectors.toList())
        );
    }
}
