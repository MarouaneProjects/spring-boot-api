package com.youcode.spring.sbootapi.dtos.response.auth;


import com.youcode.spring.sbootapi.dtos.response.auth.partials.UserJwtResponse;
import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.models.User;

public class JwtResponse extends SuccessResponse {
    private final UserJwtResponse user;
    private String scheme = "Bearer";
    private String token;

    private JwtResponse(String jwt, UserJwtResponse user) {
        this.token = jwt;
        this.user = user;
    }


    public static JwtResponse build(String jwt, User user) {
        return new JwtResponse(jwt, UserJwtResponse.build(user.getUsername(), user.getEmail(),
                user.getAuthorities(), jwt));
    }

    public UserJwtResponse getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

}
