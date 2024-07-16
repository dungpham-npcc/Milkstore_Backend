package com.cookswp.milkstore.api;

import com.cookswp.milkstore.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseData<Authentication> test(Authentication authentication) {
        // Get the authenticated user from Spring Security context
        Authentication currentPrincipal = SecurityContextHolder.getContext().getAuthentication();

        // Check if user is authenticated
        if (currentPrincipal != null && currentPrincipal.isAuthenticated()) {
            return new ResponseData<>(HttpStatus.OK.value(), "User is authenticated", currentPrincipal);
        } else {
            return new ResponseData<>(HttpStatus.FORBIDDEN.value(), "User is not authenticated", null);
        }
    }
}
