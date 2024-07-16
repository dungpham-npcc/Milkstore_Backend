package com.cookswp.milkstore.pojo.dtos.UserModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegistrationDTO {
    private int userId;
    private String roleName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private String username;
}
