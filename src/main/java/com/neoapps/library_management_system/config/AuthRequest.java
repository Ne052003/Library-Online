package com.neoapps.library_management_system.config;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
