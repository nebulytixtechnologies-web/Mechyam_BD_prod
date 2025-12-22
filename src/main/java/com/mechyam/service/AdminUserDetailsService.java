package com.mechyam.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final String adminEmail = "tejassoni910@gmail.com";
    private final String adminPassword = "admin123";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (adminEmail.equals(email)) {
            return new User(
                adminEmail,
                "{noop}" + adminPassword,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")) // Must be ROLE_ADMIN
            );
        }
        throw new UsernameNotFoundException("Admin user not found with email: " + email);
    }

    public boolean validateAdminCredentials(String email, String password) {
        return adminEmail.equals(email) && adminPassword.equals(password);
    }
}