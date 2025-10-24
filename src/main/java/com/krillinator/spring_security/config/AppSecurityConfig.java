package com.krillinator.spring_security.config;

import com.krillinator.spring_security.user.UserRole;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // TODO - Question: Why does /adminpage lead to a 403 forbidden?
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/").permitAll()       // Allow localhost:8080/
                        .requestMatchers("/debug").permitAll()  // RestController for Debugging
                        .requestMatchers("/admin").hasAuthority(UserRole.ADMIN.getUserRoleName())
        )
                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    // DEBUG USER - CREATION
    @Bean
    public UserDetailsService debugUserCreation(PasswordEncoder passwordEncoder) {

        UserDetails user = User
                .withUsername("Benny")
                .password(passwordEncoder.encode("123"))
                .authorities(UserRole.USER.getSimpleGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(user);
    }

}
