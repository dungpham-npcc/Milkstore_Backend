//package com.cookswp.milkstore.api;
//
//import com.cookswp.milkstore.response.ResponseData;
//import com.cookswp.milkstore.service.OtpService;
//import com.cookswp.milkstore.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/forgot")
//public class ForgotPasswordController {
//    private final UserService userService;
//
//    private final OtpService otpService;
//
//    @Autowired
//    public ForgotPasswordController(UserService userService, OtpService otpService){
//        this.userService = userService;
//        this.otpService = otpService;
//    }
//    @PostMapping("/otp")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseData<String> sendForgotPasswordOtp(@RequestBody String email){
//        if (email == null || email.isEmpty())
//            throw new IllegalArgumentException("Email address cannot be null or empty.");
//        if (userService.getUserByEmail(email) == null)
//            throw new DataIntegrityViolationException("Account with this email can't be found!");
//        otpService.sendForgotPasswordOtpByEmail(email);
//        return new ResponseData<>(HttpStatus.OK.value(), "OTP initialized successfully!", null);
//    }
//
//
//}
