//package com.cookswp.milkstore.api;
//
//import com.cookswp.milkstore.pojo.dtos.UserModel.UserDTO;
//import com.cookswp.milkstore.response.ResponseData;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/login")
//public class LoginController {
//    @GetMapping("/callback")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseData<String> oauth2LoginSuccess(HttpServletRequest request) throws Exception{
//        return new ResponseData<>(HttpStatus.OK.value(), "login", null);
//    }
//}
