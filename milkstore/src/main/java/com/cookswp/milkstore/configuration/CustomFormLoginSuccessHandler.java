package com.cookswp.milkstore.configuration;


import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.service.account.AccountService;
import com.cookswp.milkstore.service.user.UserService;
//import com.cookswp.milkstore.utils.JwtUtils;
import com.cookswp.milkstore.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomFormLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getUserId());
        userMap.put("email", user.getEmailAddress());
        userMap.put("username", user.getUsername());
        userMap.put("role", user.getRole().getRoleName());
        userMap.put("phone", user.getPhoneNumber());
        response.getWriter().write(String.format("{\"token\": \"%s\", \"user\": %s}", jwt, new ObjectMapper().writeValueAsString(userMap)));
        response.getWriter().flush();

//        this.setAlwaysUseDefaultTargetUrl(true);
//        this.setDefaultTargetUrl("http://localhost:8080/test");
//        super.onAuthenticationSuccess(request, response, authentication);
    }


}
