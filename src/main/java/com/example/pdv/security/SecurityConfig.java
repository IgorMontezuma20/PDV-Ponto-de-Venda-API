package com.example.pdv.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityConfig {

    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
