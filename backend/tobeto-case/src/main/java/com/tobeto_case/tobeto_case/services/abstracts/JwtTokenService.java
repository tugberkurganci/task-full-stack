package com.tobeto_case.tobeto_case.services.abstracts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobeto_case.tobeto_case.entities.Token;
import com.tobeto_case.tobeto_case.entities.User;
import com.tobeto_case.tobeto_case.repositories.TokenRepository;

import com.tobeto_case.tobeto_case.services.dtos.request.RefreshTokenRequest;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


public interface JwtTokenService {
    Token CreateToken(User user, boolean isRefresh);
    User verifyToken(String authorizationHeader);
    Token createRefeshToken(RefreshTokenRequest request);
    void deleteToken(int id);
}
