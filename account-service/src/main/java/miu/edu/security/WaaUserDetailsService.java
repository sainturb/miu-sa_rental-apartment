package miu.edu.security;

import lombok.RequiredArgsConstructor;
import miu.edu.model.User;
import miu.edu.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userDetailsService")
@Transactional
@RequiredArgsConstructor
public class WaaUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            var userDetails = new WaaUserDetails(user.get());
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found");
    }

}
