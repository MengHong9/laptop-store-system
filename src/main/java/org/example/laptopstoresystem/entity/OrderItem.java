package org.example.laptopstoresystem.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "laptop_id")
    private Long laptopId;

    @Column(name = "qty")
    private Integer quantity;


    @ManyToOne
    @JoinColumn(name = "order_id" , nullable = false)
    private Order order;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    @PrePersist
    public void prePersist() {
        created_at = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated_at = LocalDateTime.now();
    }
}
