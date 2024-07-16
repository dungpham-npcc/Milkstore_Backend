package com.cookswp.milkstore.api;


import com.cookswp.milkstore.exception.MissingRequiredFieldException;
import com.cookswp.milkstore.pojo.dtos.UserModel.PasswordUpdateDTO;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserDTO;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/account")
public class PersonalAccountController {
    private final UserService userService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonalAccountController(UserService userService,
                                     ModelMapper mapper,
                                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserRegistrationDTO> getUserProfile(){
        User user = userService.getCurrentUser();
        return new ResponseData<>(HttpStatus.OK.value(),
                user != null ?
                        "User profile retrieved" :
                        "User has not logged in",
                user != null ? mapper.map(user, UserRegistrationDTO.class) : null);
    }

    @PutMapping("/update-information")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserDTO> updatePersonalProfileInformation(UserDTO userDTO){
        if (userDTO.getPhoneNumber() == null ||
                userDTO.getUsername() == null)
            throw new MissingRequiredFieldException("Fields with asterisk");
        userService.updateUserBasicInformation(userService.getUserByEmail(userDTO.getEmailAddress()).getUserId(), mapper.map(userDTO, User.class));
        return new ResponseData<>(HttpStatus.OK.value(), "Information updated successfully!", userDTO);
    }

    @PutMapping("/password-update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> updateUserPassword(PasswordUpdateDTO passwordUpdateDTO){
        User user = userService.getCurrentUser();
        if (!passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getPassword()))
            throw new IllegalArgumentException("Old password is wrong");

        userService.updateUserPassword(userService.getUserByEmail(user.getEmailAddress()).getUserId(), passwordUpdateDTO.getNewPassword());
        return new ResponseData<>(HttpStatus.OK.value(), "Password updated successfully!", null);
    }

    @PutMapping("/forgot-password-update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> updateUserForgotPassword(String email, String newPassword){
        userService.updateUserPassword(userService.getUserByEmail(email).getUserId(), newPassword);
        return new ResponseData<>(HttpStatus.OK.value(), "Password updated successfully!", null);
    }
}
