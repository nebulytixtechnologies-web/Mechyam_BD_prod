//package com.mechyam.service;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//
//    private final String adminEmail = "tejassoni910@gmail.com";
//    private final String adminPassword = "admin123";
//	
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        if (adminEmail.equals(email)) {
//            return new User(
//                adminEmail,
//                "{noop}" + adminPassword,
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")) // Must be ROLE_ADMIN
//            );
//        }
//        throw new UsernameNotFoundException("Admin user not found with email: " + email);
//    }
//
//    public boolean validateAdminCredentials(String email, String password) {
//        return adminEmail.equals(email) && adminPassword.equals(password);
//    }
//}

package com.mechyam.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mechyam.entity.Admin;
import com.mechyam.repository.AdminRepository;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserDetailsService(AdminRepository adminRepository,
                                   PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin not found with email: " + email));

        return new User(
                admin.getEmail(),
                admin.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority(admin.getRole()) // ROLE_ADMIN
                )
        );
    }

    public boolean validateAdminCredentials(String email, String rawPassword) {

        Admin admin = adminRepository.findByEmail(email).orElse(null);

        if (admin == null) {
            return false;
        }

        return passwordEncoder.matches(rawPassword, admin.getPassword());
    }
}


    
