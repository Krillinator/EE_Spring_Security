package com.krillinator.spring_security.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DELETEME {

    @GetMapping("/who-am-i")
    public String whoAmI() {

        // Using SecurityContextHolder (global access point)
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // Using SecurityContext directly (once we have it)
        String username = authentication.getName();
        String authorities = authentication.getAuthorities().toString();

        return "Hello, " + username + "! Your roles: " + authorities;
    }

}
