package org.example.laptopstoresystem.dto.laptop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id" , "model"  , "cpu" , "ram" , "storage" , "gpu", "price" , "total_stock" , "created_at" , "updated_at"})
public class LaptopResponseDto {
    private Long id;
    private String model;
    private String ram;
    private String storage;
    private String cpu;
    private String gpu;
    private Double price;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    @JsonProperty("total_stock")
    private Long totalStock;
}
