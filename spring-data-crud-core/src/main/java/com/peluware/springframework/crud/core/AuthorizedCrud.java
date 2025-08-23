package com.peluware.springframework.crud.core;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.context.SecurityContextHolder;


public interface AuthorizedCrud {

    AuthorizationDecision GRANTED = new AuthorizationDecision(true);
    AuthorizationDecision DENIED = new AuthorizationDecision(false);

    AuthorizationManager<CrudOperation> getAuthorizationManager();

    static void verifyAccess(AuthorizedCrud authorizedCrud, final CrudOperation operation) {
        var authorizationManager = authorizedCrud.getAuthorizationManager();
        var context = SecurityContextHolder.getContext();
        authorizationManager.verify(context::getAuthentication, operation);
    }
}
