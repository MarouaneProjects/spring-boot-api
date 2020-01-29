package com.youcode.spring.sbootapi.dtos.response.users;

import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.dtos.response.users.partials.UserDataSection;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.dtos.response.users.partials.UserDataSection;
import org.springframework.data.domain.Page;

public class UsersListResponse extends SuccessResponse {
    private final UserDataSection data;

    public UsersListResponse(UserDataSection data) {
        this.data = data;
    }

    public static UsersListResponse build(Page<User> usersPage, String basePath) {

        return new UsersListResponse(
                UserDataSection.build(usersPage, basePath)
        );
    }

    public UserDataSection getData() {
        return data;
    }
}
