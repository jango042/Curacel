package com.curacel.vehiclelisting.services;

import com.curacel.vehiclelisting.pojo.UsersPojo;
import com.curacel.vehiclelisting.util.MessageResponses;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersServices extends UserDetailsService {

    public MessageResponses createUser(UsersPojo user);
}
