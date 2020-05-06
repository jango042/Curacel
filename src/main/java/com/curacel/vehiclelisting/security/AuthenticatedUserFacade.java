package com.curacel.vehiclelisting.security;

import com.curacel.vehiclelisting.model.Users;
import org.springframework.security.core.Authentication;


public interface AuthenticatedUserFacade {
    Authentication getAuthentication();

    String getName();
    Users getUser();
}
