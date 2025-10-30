package com.krillinator.spring_security.config;

import com.krillinator.spring_security.user.authority.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final PasswordEncoder passwordEncoder; // This will inject AppPasswordConfig BY DEFAULT (No Bean Collision)

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        /* TODO - If we try to move to a resource that isn't available, we have to login to get a 404
        *   This is unclear and can be made better
        *   Why login to see a 404? Is this secure?
        * */

        // TODO Memory Storage Attack - https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/erasure.html

        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        // .requestMatchers() // TODO - check against specific HTTP METHOD
                        .requestMatchers("/", "/register").permitAll()  // Allow localhost:8080/
                        .requestMatchers("/debug/**").permitAll()       // RestController for Debugging
                        .requestMatchers("/admin", "/tools").hasRole("ADMIN")
                        .requestMatchers("/user").hasRole(UserRole.USER.name())
                        .anyRequest().authenticated() // MUST exist AFTER matchers, TODO - Is this true by DEFAULT?
        )
                .formLogin(Customizer.withDefaults());  // Use Default FormLogin Settings / Authentication

        return httpSecurity.build();
    }

}
