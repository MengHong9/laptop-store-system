package org.example.laptopstoresystem.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh-token")
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "token" , nullable = false)
    private String token;

    @Column(name = "expiresAt" , nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "revoked" , nullable = false)
    private boolean revoked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @PrePersist
    public  void preCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }


    public boolean isExpiresAt(){
        return LocalDateTime.now().isAfter(expiresAt);
    }


    public boolean isValid(){
        return !isExpiresAt() && !revoked;
    }


}
