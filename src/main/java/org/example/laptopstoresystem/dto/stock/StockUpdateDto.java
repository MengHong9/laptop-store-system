package org.example.laptopstoresystem.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockUpdateDto {


    @NotNull(message = "operation types is required")
    @JsonProperty("operation_types")
    @Min(value = 1 , message = "operation types must be (1,2)")
    @Max(value = 2 , message = "operation types must be (1,2)")
    private Integer operationTypes;


    @NotNull(message = "stock quantity is required")
    @JsonProperty("stock_quantity")
    @Min(value= 1 , message = "stock quantity at least 1")
    private Integer stockQuantity;

}
