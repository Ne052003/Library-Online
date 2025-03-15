package com.neoapps.library_management_system.dto;

import com.neoapps.library_management_system.entities.Role;
import com.neoapps.library_management_system.entities.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserDTO {
    private final Long id;
    private final String fullName;
    private final String email;
    private final Role role;
    private final LocalDateTime createdAt;

    public UserDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
    }
}
