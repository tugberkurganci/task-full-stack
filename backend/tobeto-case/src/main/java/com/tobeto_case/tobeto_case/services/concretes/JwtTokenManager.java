package com.tobeto_case.tobeto_case.services.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobeto_case.tobeto_case.entities.Token;
import com.tobeto_case.tobeto_case.entities.User;
import com.tobeto_case.tobeto_case.repositories.TokenRepository;

import com.tobeto_case.tobeto_case.services.abstracts.JwtTokenService;
import com.tobeto_case.tobeto_case.services.abstracts.UserService;
import com.tobeto_case.tobeto_case.services.dtos.request.RefreshTokenRequest;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtTokenManager implements JwtTokenService {


    private String jwtKey;
    SecretKey key;
    private ObjectMapper objectMapper;

    private UserService userService;
    private TokenRepository tokenRepository;

    public JwtTokenManager(@Value("${jwt.key}") String jwtKey, UserService userService, TokenRepository tokenRepository) {

        this.jwtKey = jwtKey;
        this.key = Keys.hmacShaKeyFor(jwtKey.getBytes());
        this.objectMapper = new ObjectMapper();
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }


    public Token CreateToken(User user, boolean isRefresh) {
        TokenSubject tokenSubject=new TokenSubject(user.getId());
        long expirationMillisForBaseToken = System.currentTimeMillis() + (24 * 60 * 60 * 1000);


        long expirationMillisForRefreshToken = System.currentTimeMillis() + ( 5*60 * 1000);
        Date expirationDateForRefresh = new Date(expirationMillisForRefreshToken);
        Date expirationDateForBase = new Date(expirationMillisForBaseToken);
        try {
            String subject=objectMapper.writeValueAsString(tokenSubject);
            String refreshToken= Jwts.builder().setSubject(subject).setExpiration(expirationDateForRefresh).signWith(key).compact();
            String baseToken= Jwts.builder().setSubject(subject).setExpiration(expirationDateForBase).signWith(key).compact();
            Token token=new Token(refreshToken,baseToken,"Bearer",user);
            if(isRefresh)tokenRepository.save(token);

            //if we want to renew base token when refresh token renew
            //tokenRepository.deleteByUserId(user.getId());
            // tokenRepository.save(token);
            return token ;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    public User verifyToken(String authorizationHeader) {
        if(authorizationHeader==null) return null;
        String token = authorizationHeader.split(" ")[1];
        JwtParser parser=Jwts.parserBuilder().setSigningKey(key).build();
        try {
            Jws<Claims> claims= parser.parseClaimsJws(token);
            var subject=claims.getBody().getSubject();
            var tokenSubject= objectMapper.readValue(subject,TokenSubject.class);

            User user=userService.getEntityUserById(tokenSubject.id);
            return user;
        }catch (JwtException ex){
            ex.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;


    }

    public Token createRefeshToken(RefreshTokenRequest request) {

        Token token=tokenRepository.findByUserId(request.userId());
        User user=verifyToken("Bearer "+token.getBaseToken());
        if(user!=null) {
            Token token2=CreateToken(user,false);
            token.setRefreshToken(token2.getRefreshToken());
            return token;
        };
        return null;



    }
   @Transactional
    public void deleteToken(int id) {
        tokenRepository.deleteByUserId(id);
    }


    public static record TokenSubject (int id){}
}