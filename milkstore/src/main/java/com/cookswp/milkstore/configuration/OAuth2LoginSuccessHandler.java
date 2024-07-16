package com.cookswp.milkstore.configuration;

import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.service.role.RoleService;
import com.cookswp.milkstore.service.user.UserService;
import com.cookswp.milkstore.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private final UserService userService;
    @Autowired
    private final RoleService roleService;
    @Autowired
    private final JwtUtils jwtUtils;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = principal.getAttributes();
        String email = attributes.getOrDefault("email", "").toString();

        User user = userService.getUserByEmail(email);

        if (user != null) {

            if (!user.isEnabled() || !user.isAccountNonLocked()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"message\": \" Tài khoản này có thể đã bị xóa hoặc bị hạn chế, vui lòng liên hệ quản trị viên để biết thông tin chi tiết.\"}");
                return;
            }

            String jwt = jwtUtils.generateJwtToken(getAuthentication(user, attributes));
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getUserId());
            userMap.put("email", user.getEmailAddress());
            userMap.put("username", user.getUsername());
            userMap.put("role", user.getRole().getRoleName());
            userMap.put("phone", user.getPhoneNumber());
            response.getWriter().write(String.format("{\"token\": \"%s\", \"user\": %s}", jwt, new ObjectMapper().writeValueAsString(userMap)));
            response.getWriter().flush();

        } else {

            User newUser = new User();
            newUser.setEmailAddress(email);
            userService.registerUser(newUser);

            response.sendRedirect("http://localhost:3000/fill-oauth2?auth=" + jwtUtils.buildTempToken(email));

        }

    }

    private static Authentication getAuthentication(User user, Map<String, Object> attributes) {
        user.setAttributes(attributes);
        return new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword() == null ? null : user.getPassword(),
                user.getAuthorities()
        );
    }

}
