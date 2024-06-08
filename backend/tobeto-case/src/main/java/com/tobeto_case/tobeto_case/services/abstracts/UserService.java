package com.tobeto_case.tobeto_case.services.abstracts;

import com.tobeto_case.tobeto_case.entities.User;
import com.tobeto_case.tobeto_case.services.dtos.request.CreateUserRequest;

public interface UserService {


    User findByEmail(String username);

    User getEntityUserById(int userId);

    void add(CreateUserRequest createUserRequest);

}