//package com.cookswp.milkstore.configuration;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@AllArgsConstructor
//public class TempSecurityConfig {
//    @Autowired
//    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
//    @Autowired
//    private final CustomFormLoginSuccessHandler customFormLoginSuccessHandler;
//    @Autowired
//    private final CustomAuthenticationProvider customAuthenticationProvider;
//    @Bean
//    public AuthenticationManager authenticationManager(){
//        return new ProviderManager(Collections.singletonList(customAuthenticationProvider));
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/register", "/register/**", "/account").permitAll();
//                    auth.anyRequest().authenticated();
//                }) // Allow all requests for testing
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .formLogin(form -> {
//                    form.successHandler(customFormLoginSuccessHandler); // Use custom form login success handler
//                })
//                .oauth2Login(oauth2 ->{
//                    oauth2.successHandler(oAuth2LoginSuccessHandler);
//                })
//                .build();
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
//                new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return urlBasedCorsConfigurationSource;
//    }
//}