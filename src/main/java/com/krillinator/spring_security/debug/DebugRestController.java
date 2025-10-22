package com.krillinator.spring_security.debug;

import com.krillinator.spring_security.config.AppPasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
public class DebugRestController {

    // private final AppPasswordConfig appPasswordConfig; // ANTI-PATTERN (This is a config)
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DebugRestController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
