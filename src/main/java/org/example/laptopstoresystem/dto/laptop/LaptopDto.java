package org.example.laptopstoresystem.dto.laptop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class LaptopDto {


    @NotBlank(message = "model is required")
    private String model;

    @NotBlank(message = "ram is required")
    private String ram;

    @NotBlank(message = "storage is required")
    private String storage;

    @NotBlank(message = "cpu is required")
    private String cpu;


    @NotNull(message = "price is required")
    @Positive(message = "price must be positive")
    private Double price;


    private String gpu;
}
