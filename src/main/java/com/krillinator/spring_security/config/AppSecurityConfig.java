package com.krillinator.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // TODO - Question: Why does /adminpage lead to a 403 forbidden?
        httpSecurity.authorizeHttpRequests(
                auth -> auth.requestMatchers("/").permitAll()   // Allow localhost:8080/
                        .requestMatchers("").blacklist("ADMIN, BATMAN")

                /* ROLES
                *   GUEST
                *   USER
                *   ADMIN
                *   BATMAN
                * */
        );

        return httpSecurity.build();
    }

    // DEBUG USER - CREATION
    @Bean
    public UserDetailsService debugUserCreation() {

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("Benny")
                .password("123")
                .roles("USER")  // ROLE_USER // TODO - Check if you can have multiple roles
                .build();

        return new InMemoryUserDetailsManager(user);
    }

}
