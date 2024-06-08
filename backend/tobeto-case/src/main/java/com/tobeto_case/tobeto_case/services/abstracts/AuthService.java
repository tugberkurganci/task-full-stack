package com.tobeto_case.tobeto_case.services.abstracts;

import com.tobeto_case.tobeto_case.services.dtos.request.Credentials;
import com.tobeto_case.tobeto_case.services.dtos.response.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(Credentials credentials);

}
