package com.cookswp.milkstore.configuration;

import com.cookswp.milkstore.exception.UserInvisibilityException;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.getUserByEmail(email);

        try {

            if (user == null){
                throw new UsernameNotFoundException("User not found!");
            }

            else if (!passwordEncoder.matches(password, user.getPassword()))
                throw new BadCredentialsException("Invalid credentials!");

            else if (!user.isEnabled() || !user.isAccountNonLocked())
                throw new UserInvisibilityException("Tài khoản này có thể đã bị xóa hoặc bị hạn chế, vui lòng liên hệ quản trị viên để biết thông tin chi tiết.");

        } catch (UsernameNotFoundException | BadCredentialsException | UserInvisibilityException e) {
            System.out.println("Validation exceptions: " + e.getMessage());
            throw e;
        }
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

