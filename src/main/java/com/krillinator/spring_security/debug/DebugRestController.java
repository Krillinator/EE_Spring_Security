package com.krillinator.spring_security.debug;

import com.krillinator.spring_security.config.AppPasswordConfig;
import com.krillinator.spring_security.user.CustomUser;
import com.krillinator.spring_security.user.CustomUserRepository;
import com.krillinator.spring_security.user.authority.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/debug")
public class DebugRestController {

    // private final AppPasswordConfig appPasswordConfig; // ANTI-PATTERN (This is a config)
    private final PasswordEncoder passwordEncoder;
    private final CustomUserRepository customUserRepository;

    @Autowired
    public DebugRestController(PasswordEncoder passwordEncoder, CustomUserRepository customUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customUserRepository = customUserRepository;
    }

    @GetMapping("/create-debug-user")
    public ResponseEntity<String> createNewDebugCustomUser() {

        try {
            customUserRepository.save(
                    new CustomUser(
                            "Benny",
                            passwordEncoder.encode("123"),
                            true,
                            true,
                            true,
                            true,
                            Set.of(UserRole.ADMIN)
                    )
            );

            return ResponseEntity.ok().body("New User was created");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Username already exists" + ex);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        } finally {
            System.out.println("Process: creating new debug user, finishing.");
        }
    }

    @GetMapping
    public ResponseEntity<String> testBcryptEncoding(
            @RequestParam(value = "message") String message         // ?message=""
    ) {

        String obfuscatedMessage = passwordEncoder.encode(message); // Turn Message into BCrypt Hash

        return ResponseEntity.ok().body(
                "Message was: " + message + " and was hashed into " + obfuscatedMessage
                // Same passwords, yield different results (Safety through Salting)
                // $2a$10$EIQjuLSX6iXknFlK0j/bSu6BlFcfJCvXw4N85JpnW1OXmmoIMyuJ. // 72 Length
                // $2a = Version
                // $10 = Strength (Iteration)
                // Salt = EIQjuLSX6iXknFlK (16 Length)
                // 0j/bSu6BlFcfJCvXw4N85JpnW1OXmmoIMyuJ. (37 Length???)
        );
    }

}
