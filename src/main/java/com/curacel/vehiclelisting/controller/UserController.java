package com.curacel.vehiclelisting.controller;

import com.curacel.vehiclelisting.pojo.LoginDetailsPojo;
import com.curacel.vehiclelisting.pojo.UsersPojo;
import com.curacel.vehiclelisting.services.UsersServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersServices usersServices;


    @ApiOperation(value = "Add user Endpoint", notes = "Add User to the database")
    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody UsersPojo user){
        return ResponseEntity.ok(usersServices.createUser(user));
    }

    @ApiOperation("User login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Response Headers")})
    @PostMapping("/login")
    public void Login(@RequestBody LoginDetailsPojo loginRequestModel) {
        throw new IllegalStateException("This Method should not be called!");
    }
}
