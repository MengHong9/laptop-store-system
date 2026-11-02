package org.example.laptopstoresystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "laptop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"stocks"})
@EqualsAndHashCode(exclude = {"stocks"})
@Entity
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private Double price;
    private String ram;
    private String storage;
    private String cpu;
    private String gpu;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    @OneToMany(mappedBy = "laptop" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Stock> stocks;


    @Transient
    public Long getTotalStocks() {
        if(stocks == null || stocks.isEmpty()){
            return 0L;
        }
        return stocks.stream()
                .filter(stock -> stock.getStockQuantity() != null)
                .mapToLong(Stock::getStockQuantity)
                .sum();
    }

    @PrePersist
    public void preCreatedAt() {
        created_at = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdatedAt() {
        updated_at = LocalDateTime.now();
    }
}
