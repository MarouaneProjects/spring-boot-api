package com.youcode.spring.sbootapi.services.interfaces;

import com.youcode.spring.sbootapi.enums.AuthorizationPolicy;

public interface ISettingsService {

    int getPageSize();

    AuthorizationPolicy getWhoCanEditProducts();

    String getAdminRoleName();

    String getAnonymousRoleName();

    AuthorizationPolicy getWhoCanDeleteProducts();

    String getDefaultAdminEmail();

    String getDefaultAdminUsername();

    String getDefaultAdminPassword();

    String getDefaultAdminLastName();

    String getDefaultAdminFirstName();

    AuthorizationPolicy getWhoCanCreateComments();

    AuthorizationPolicy getWhoCanUpdateComments();

    AuthorizationPolicy getWhoCanDeleteComments();

    String getAuthenticatedRoleName();

    int getMaxUsersToSeed();

    int getMaxProductsToSeed();

    int getMaxTagsToSeed();

    AuthorizationPolicy getWhocanCheckout();

    AuthorizationPolicy getWhoCanEditComments();

    AuthorizationPolicy getWhoCanCreateProducts();

    AuthorizationPolicy getWhoCanUpdateProducts();

    int getMaxCommentsToSeed();

    String getUploadsDirectory();

    int getMaxCategoriesToSeed();

    int getOrdersToSeedCount();
}
