package miu.edu.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private List<RoleDTO> roles;
}
