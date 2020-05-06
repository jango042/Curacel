package com.curacel.vehiclelisting.services.impl;

import com.curacel.vehiclelisting.model.Users;
import com.curacel.vehiclelisting.pojo.UsersPojo;
import com.curacel.vehiclelisting.repository.UsersRepository;
import com.curacel.vehiclelisting.services.UsersServices;
import com.curacel.vehiclelisting.util.MessageResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServicesImpl implements UsersServices {

    @Autowired
    private UsersRepository usersRepo;

    @Autowired
    private PasswordEncoder bcryptEcoder;

    @Override
    public MessageResponses createUser(UsersPojo user) {
        try {
            Users users = new ModelMapper().map(user, Users.class);
            users.setRegDate(LocalDateTime.now());
            users.setPassword(bcryptEcoder.encode(user.getPassword()));
            usersRepo.save(users);
            return MessageResponses.response(MessageResponses.CODE_OK, "User created successfully");
        } catch (Exception e){
            return MessageResponses.response(MessageResponses.CODE_ERROR, "Error Occurred");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = usersRepo.findByUsername(username);

        List<Users> allUsers = usersRepo.findAll();

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }else {
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                    user.get().getPassword(), true, true, true, true, new ArrayList<>());

        }
    }
}
