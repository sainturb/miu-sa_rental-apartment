package miu.edu.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import miu.edu.model.Role;
import miu.edu.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Component
public class WaaUserDetails implements UserDetails {
    private String username;
    @JsonIgnore
    private String password;

    private List<String> roles;

    public WaaUserDetails() {}

    public WaaUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toList());
    }

    public WaaUserDetails(Map<String, Object> claims) {
        this.username = (String) claims.get("username");
        this.password = null;
        try {
            this.roles = (List<String>) claims.get("roles");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
