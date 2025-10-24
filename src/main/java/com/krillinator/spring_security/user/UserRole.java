package com.krillinator.spring_security.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.krillinator.spring_security.user.UserPermission.*;

public enum UserRole {

    GUEST(
            UserRoleName.GUEST.getRoleName(),
            Set.of(
                    // PERMISSIONS
            )
    ),

    USER(
            UserRoleName.USER.getRoleName(),
            Set.of(
                    // PERMISSIONS
                    GET_TODO, 
                    POST_TODO, 
                    DELETE_TODO, 
                    PUT_TODO)
    ),
    ADMIN(
            UserRoleName.ADMIN.getRoleName(),
            Set.of(
                    // PERMISSIONS
                    MANAGE_USERS
            ));

    private final String userRoleName;
    private final Set<UserPermission> userPermissions;

    UserRole(String userRoleName, Set<UserPermission> userPermissions) {
        this.userRoleName = userRoleName;
        this.userPermissions = userPermissions;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public Set<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public List<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(this.userRoleName));
        simpleGrantedAuthorityList.addAll(
                this.userPermissions.stream().map(
                        userPermission -> new SimpleGrantedAuthority(userPermission.getPermission())
                ).toList()
        );

        return List.copyOf(simpleGrantedAuthorityList);
    }

}
