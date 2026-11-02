package org.example.laptopstoresystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.laptopstoresystem.common.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    @PrePersist
    public void preCreatedAt() {
        created_at = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdatedAt() {
        updated_at = LocalDateTime.now();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getUsername() {
        return this.name;
    }
}
