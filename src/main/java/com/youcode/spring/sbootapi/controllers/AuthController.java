package com.youcode.spring.sbootapi.controllers;

import com.youcode.spring.sbootapi.config.JwtProvider;
import com.youcode.spring.sbootapi.dtos.request.LoginDto;
import com.youcode.spring.sbootapi.dtos.request.RegisterDto;
import com.youcode.spring.sbootapi.dtos.response.auth.JwtResponse;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.dtos.request.LoginDto;
import com.youcode.spring.sbootapi.dtos.request.RegisterDto;
import com.youcode.spring.sbootapi.dtos.response.auth.JwtResponse;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {

    @Autowired
    UsersController usersController;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping({"auth/register"})
    public ResponseEntity<AppResponse> register(@Valid @RequestBody RegisterDto dto, BindingResult bindingResult) {
        return usersController.registerUser(dto, bindingResult);
    }

    @PostMapping({"auth/login", "users/login"})
    public ResponseEntity<AppResponse> login(@Valid @RequestBody LoginDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        User userPrinciple = (User) authentication.getPrincipal();

        User user = ((User) authentication.getPrincipal());
        return ResponseEntity.ok(JwtResponse.build(jwt, user));
    }
}
