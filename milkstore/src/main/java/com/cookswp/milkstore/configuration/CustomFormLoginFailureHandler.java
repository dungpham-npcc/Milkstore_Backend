package com.cookswp.milkstore.configuration;

import com.cookswp.milkstore.exception.UserInvisibilityException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CustomFormLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED; // Default status code

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "User not found!";
            statusCode = HttpServletResponse.SC_NOT_FOUND;
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid username or password!";
        } else if (exception instanceof UserInvisibilityException) {
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "Login failed!";
        }

        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String encodedMessage = new String(errorMessage.getBytes(), StandardCharsets.UTF_8);
        response.getWriter().write("{\"message\": \"" + encodedMessage + "\"}");
        response.getWriter().flush();
    }
}
