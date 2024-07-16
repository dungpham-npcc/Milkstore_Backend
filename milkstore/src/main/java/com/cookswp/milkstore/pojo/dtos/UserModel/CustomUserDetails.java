//package com.cookswp.milkstore.pojo.dtos.UserModel;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collection;
//import java.util.Map;
//
//@AllArgsConstructor
//public class CustomUserDetails implements UserDetails, OAuth2User {
//    @Getter
//    private String phoneNumber;
//    private String name;
//    private String email;
//    private String password;
//    private Collection<? extends GrantedAuthority> authorities;
//    private Map<String, Object> attributes;
//
//    @Override
//    public String getName() {
//        return email;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return name;
//    }
//}
