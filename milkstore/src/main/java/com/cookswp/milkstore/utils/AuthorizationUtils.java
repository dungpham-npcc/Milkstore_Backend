package com.cookswp.milkstore.utils;

import com.cookswp.milkstore.exception.UnauthorizedAccessException;
import com.cookswp.milkstore.pojo.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationUtils {

    public static void checkAuthorization(String requiredRole) throws UnauthorizedAccessException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentUser.getRole().getRoleName().equals(requiredRole)) {
            throw new UnauthorizedAccessException();
        }
    }

    public static void checkAuthorization(String requiredRole1, String requiredRole2) throws UnauthorizedAccessException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentUser.getRole().getRoleName().equals(requiredRole1) &&
                !currentUser.getRole().getRoleName().equals(requiredRole2)) {
            throw new UnauthorizedAccessException();
        }
    }

    public static void checkAuthorization(String requiredRole1, String requiredRole2, String requiredRole3) throws UnauthorizedAccessException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentUser.getRole().getRoleName().equals(requiredRole1) &&
                !currentUser.getRole().getRoleName().equals(requiredRole2) &&
                !currentUser.getRole().getRoleName().equals(requiredRole3)) {
            throw new UnauthorizedAccessException();
        }
    }

    public static void checkAuthorization(String requiredRole1, String requiredRole2, String requiredRole3, String requiredRole4) throws UnauthorizedAccessException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentUser.getRole().getRoleName().equals(requiredRole1) &&
                !currentUser.getRole().getRoleName().equals(requiredRole2) &&
                !currentUser.getRole().getRoleName().equals(requiredRole3) &&
                !currentUser.getRole().getRoleName().equals(requiredRole4)) {
            throw new UnauthorizedAccessException();
        }
    }

}
