package org.example.laptopstoresystem.dto.laptop;


import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class LaptopUpdateDto {

    private String model;

    private String ram;

    private String storage;

    private String cpu;

    private String gpu;

    @Positive(message = "price must be positive")
    private Double price;
}
