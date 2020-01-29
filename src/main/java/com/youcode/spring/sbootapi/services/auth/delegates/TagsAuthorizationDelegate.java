package com.youcode.spring.sbootapi.services.auth.delegates;

import com.youcode.spring.sbootapi.models.Tag;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.services.auth.AuthorizationService;

public class TagsAuthorizationDelegate {
    private final AuthorizationService authorizationService;

    public TagsAuthorizationDelegate(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public boolean canCreateTags(User user) {
        return this.authorizationService.isUserAdmin(user);
    }

    public boolean canUpdateTag(User user, Tag tag) {
        return this.authorizationService.isUserAdmin(user);
    }

    public boolean canDeleteTag(User user) {
        return this.authorizationService.isUserAdmin(user);
    }

    public boolean canReadTag(User user) {
        return true;
    }
}
