package com.youcode.spring.sbootapi.controllers;

import com.youcode.spring.sbootapi.dtos.request.RegisterDto;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.models.Role;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.services.RolesService;
import com.youcode.spring.sbootapi.services.auth.UsersService;
import com.youcode.spring.sbootapi.dtos.request.RegisterDto;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    RolesService rolesService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<AppResponse> registerUser(@Valid @RequestBody RegisterDto createUserDto, BindingResult result) {
        if (usersService.existsByUsername(createUserDto.getUsername())) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("username", "Username already taken");
            return new ResponseEntity<AppResponse>(new ErrorResponse(errors),
                    HttpStatus.BAD_REQUEST);
        }

        if (usersService.existsByEmail(createUserDto.getEmail())) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("email", "Email already taken");
            return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }

        HashSet<Role> roles = new HashSet<Role>(Collections.singletonList(rolesService.getOrCreate("ROLE_USER")));

        // Creating user's account
        User user = new User(createUserDto.getFirstName(), createUserDto.getLastName(), createUserDto.getEmail(),
                createUserDto.getUsername(), createUserDto.getPassword(), roles);

        usersService.createUser(user);

        return new ResponseEntity<>(new SuccessResponse("User registered successfully"), HttpStatus.OK);

    }

}
