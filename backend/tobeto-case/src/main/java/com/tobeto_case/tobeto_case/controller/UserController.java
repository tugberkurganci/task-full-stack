package com.tobeto_case.tobeto_case.controller;

import com.tobeto_case.tobeto_case.services.abstracts.UserService;
import com.tobeto_case.tobeto_case.services.dtos.request.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private  UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public void add(@RequestBody @Valid CreateUserRequest createUserRequest){
        userService.add(createUserRequest);
    }}