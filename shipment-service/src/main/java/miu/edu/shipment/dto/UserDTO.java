package miu.edu.shipment.dto;

import lombok.Data;
import miu.edu.dto.RoleDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String preferredPayment;
    private List<RoleDTO> roles;

    public Map<String, Object> toMap() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", this.id);
        claims.put("username", this.username);
        claims.put("email", this.email);
        claims.put("fullname", String.format("%s %s", this.lastname, this.firstname));
        claims.put("payment", this.preferredPayment);
        claims.put("roles", roles.stream().map(RoleDTO::getRole).collect(Collectors.toList()));
        return claims;
    }
}
