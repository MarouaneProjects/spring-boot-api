package com.youcode.spring.sbootapi.services.auth.delegates;

import com.youcode.spring.sbootapi.models.Category;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.services.auth.AuthorizationService;

public class CategoriesAuthorizationDelegate {
    private final AuthorizationService authorizationService;

    public CategoriesAuthorizationDelegate(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public boolean canCreateCategories(User user) {
        return this.authorizationService.isUserAdmin(user);
    }

    public boolean canUpdateCategories(User user, Category category) {
        return this.authorizationService.isUserAdmin(user);
    }

    public boolean canDeleteCategories(User user) {
        return this.authorizationService.isUserAdmin(user);
    }

    public boolean canReadCategories(User user) {
        return true;
    }
}
