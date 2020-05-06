package com.curacel.vehiclelisting.pojo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsersPojo {

    private Long id;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
}
