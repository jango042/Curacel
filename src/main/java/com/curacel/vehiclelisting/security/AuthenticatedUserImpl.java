package com.curacel.vehiclelisting.security;

import com.curacel.vehiclelisting.model.Users;
import com.curacel.vehiclelisting.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserImpl implements AuthenticatedUserFacade {

    @Autowired
    private UsersRepository userRepo;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getName() {
        return this.getAuthentication().getName();
    }

    @Override
    public Users getUser() {
        return this.getName() != null ? userRepo.findByUsername(this.getName()).get() : null;
    }


}
