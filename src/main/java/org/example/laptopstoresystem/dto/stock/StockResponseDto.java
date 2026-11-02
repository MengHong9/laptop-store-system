package org.example.laptopstoresystem.dto.stock;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockResponseDto {
    private Long StockId;

    @JsonProperty("laptop_id")
    private Long laptopId;


    @JsonProperty("stock_quantity")
    private Integer stockQuantity;


    @JsonProperty("created_date")
    private LocalDateTime createdDate;


    @JsonProperty("updated_date")
    private LocalDateTime updatedDate;
}
