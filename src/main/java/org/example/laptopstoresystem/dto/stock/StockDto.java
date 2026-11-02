package org.example.laptopstoresystem.dto.stock;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockDto {

    @NotNull(message = "laptop id is required")
    private Long laptop_id;


    @NotNull(message = "stock quantity is required")
    @JsonProperty("stock_quantity")
    private Integer stockQuantity;
}
