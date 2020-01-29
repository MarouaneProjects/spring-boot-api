package com.youcode.spring.sbootapi.admin.dtos.users.partials;

import com.youcode.spring.sbootapi.dtos.response.users.partials.UserIdAndUsernameDto;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.dtos.response.users.partials.UserIdAndUsernameDto;
import com.youcode.spring.sbootapi.models.User;

public class AdminUserSummary extends UserIdAndUsernameDto {
    private final String email;

    public AdminUserSummary(Long id, String username, String email) {
        super(id, username);
        this.email=email;
    }

    public static AdminUserSummary build(User user) {
        return new AdminUserSummary(user.getId(), user.getUsername(), user.getEmail());
    }

    public String getEmail() {
        return email;
    }
}
