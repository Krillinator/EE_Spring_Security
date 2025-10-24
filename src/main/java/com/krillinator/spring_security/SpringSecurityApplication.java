package com.krillinator.spring_security;

import com.krillinator.spring_security.user.UserRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);

		System.out.println(UserRole.GUEST.getSimpleGrantedAuthorities());
		System.out.println(UserRole.USER.getSimpleGrantedAuthorities());
		System.out.println(UserRole.ADMIN.getSimpleGrantedAuthorities());
	}

}
