package miu.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import miu.edu.security.JwtHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class UaaServiceImpl implements UaaService {

    private final JwtHelper jwtHelper;
    private final AuthenticationManager manager;
    private final UserServiceImpl userService;

    @Override
    public Map<String, String> login(Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        var request = new UsernamePasswordAuthenticationToken(username, password);
        var userInfo = userService.getByUsername(username);
        if (userInfo.isPresent()) {
            final String accessToken = jwtHelper.generateToken(username, userInfo.get().toMap());
            final String refreshToken = jwtHelper.generateRefreshToken(username);
            Map<String, String> tokenBody = new HashMap<>();
            tokenBody.put("access_token", accessToken);
            tokenBody.put("refresh_token", refreshToken);
            manager.authenticate(request);
            return tokenBody;
        } else {
            throw new UsernameNotFoundException("not found");
        }
    }

    @Override
    public Map<String, String> refresh(String refreshToken) {
        boolean isRefreshTokenValid = jwtHelper.validateToken(refreshToken);
        if (isRefreshTokenValid) {
            var username = jwtHelper.getSubject(refreshToken);
            var userInfo = userService.getByUsername(username);
            if (userInfo.isPresent()) {
                final String accessToken = jwtHelper.generateToken(username, userInfo.get().toMap());
                Map<String, String> tokenBody = new HashMap<>();
                tokenBody.put("access_token", accessToken);
                tokenBody.put("refresh_token", jwtHelper.generateRefreshToken(username));
                return tokenBody;
            } else {
                throw new UsernameNotFoundException("not found");
            }
        }
        throw new UsernameNotFoundException("not found");
    }

    @Override
    public Map<String, Boolean> validate() {
        return new HashMap<>();
    }
}
