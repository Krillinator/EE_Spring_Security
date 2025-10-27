package com.krillinator.spring_security;

import com.krillinator.spring_security.user.authority.UserRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);

		System.out.println(
				UserRole.GUEST.getRoleName()	// ROLE_GUEST
		);

		System.out.println(
				UserRole.USER.getUserPermissions() // PERMISSIONS
		);

		System.out.println(
				UserRole.ADMIN.getUserPermissions()
		);

		System.out.println(
				UserRole.GUEST.getUserAuthorities() + " \n " + // GUEST - AUTHORITY
				UserRole.USER.getUserAuthorities() + " \n " + // USER - AUTHORITY incl. perms.
				UserRole.ADMIN.getUserAuthorities()			 // ADMIN - AUTHORITY incl. perms.
		);

	}

}
