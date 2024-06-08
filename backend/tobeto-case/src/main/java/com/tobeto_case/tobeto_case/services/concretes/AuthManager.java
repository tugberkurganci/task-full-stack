package com.tobeto_case.tobeto_case.services.concretes;



import com.tobeto_case.tobeto_case.core.exception.AuthenticationException;
import com.tobeto_case.tobeto_case.entities.Token;
import com.tobeto_case.tobeto_case.entities.User;
import com.tobeto_case.tobeto_case.services.abstracts.AuthService;
import com.tobeto_case.tobeto_case.services.abstracts.JwtTokenService;
import com.tobeto_case.tobeto_case.services.abstracts.UserService;
import com.tobeto_case.tobeto_case.services.dtos.request.Credentials;
import com.tobeto_case.tobeto_case.services.dtos.response.AuthResponse;
import com.tobeto_case.tobeto_case.services.dtos.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthManager implements AuthService {

    private UserService userService;


    private JwtTokenService jwtService;

    private PasswordEncoder passwordEncoder ;

    public AuthManager(UserService userService, JwtTokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }



    private void checkUserExist(User user) {
        if (user == null) {
            throw new AuthenticationException();
        }
    }

    private void checkPasswordMatch(String password, String password2) {
        if (!passwordEncoder.matches(password, password2)) {
            throw new AuthenticationException();
        }
    }


    @Override
    public AuthResponse authenticate(Credentials credentials) {
        User user = userService.findByEmail(credentials.email());
        checkUserExist(user);
        checkPasswordMatch(credentials.password(),user.getPassword());
        jwtService.deleteToken(user.getId());
        Token token=jwtService.CreateToken(user,true);
        return new AuthResponse(new UserResponse(user),token);
    }
}