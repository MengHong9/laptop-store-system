package org.example.laptopstoresystem.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;

    @PrePersist
    public void createdDate(){
        createdDate = LocalDateTime.now();
    }


    @PreUpdate
    public void updatedDate(){
        updatedDate = LocalDateTime.now();
    }

}
