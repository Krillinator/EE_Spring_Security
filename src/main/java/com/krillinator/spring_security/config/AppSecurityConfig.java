package com.krillinator.spring_security.config;

import com.krillinator.spring_security.user.authority.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        // .requestMatchers() // TODO - check against specific HTTP METHOD
                        .requestMatchers("/").permitAll()       // Allow localhost:8080/
                        .requestMatchers("/debug").permitAll()  // RestController for Debugging
                        .requestMatchers("/admin", "/tools").hasRole("ADMIN")
                        .requestMatchers("/user").hasRole(UserRole.USER.name())
                        .anyRequest().authenticated() // MUST exist AFTER matchers, TODO - Is this true by DEFAULT?
                        // TODO - RequestMatcher for has ALL authorities
        )
                .formLogin(Customizer.withDefaults());  // Use Default FormLogin Settings / Authentication

        return httpSecurity.build();
    }

    // DEBUG USER - CREATION
    @Bean
    public UserDetailsService debugUserCreation() {

        UserDetails benny = User.builder()
                .username("Benny")
                .password(passwordEncoder.encode("123"))
                .authorities(UserRole.USER.getUserAuthorities())
                // .passwordEncoder(passwordEncoder::encode)
                // .roles("USER")  // ROLE_USER // TODO - Check if you can have multiple roles
                .build();

        UserDetails frida = User.builder()
                .username("Frida")
                .password(passwordEncoder.encode("321"))
                .authorities(UserRole.ADMIN.getUserAuthorities())
                // .passwordEncoder(passwordEncoder::encode)
                // .roles("USER")  // ROLE_USER // TODO - Check if you can have multiple roles
                .build();

        return new InMemoryUserDetailsManager(benny, frida);
    }

}
