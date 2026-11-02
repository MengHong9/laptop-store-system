package org.example.laptopstoresystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.laptopstoresystem.common.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @PrePersist
    public void preCreatedAt() {
        created_at = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdatedAt() {
        updated_at = LocalDateTime.now();
    }
}
