package org.example.laptopstoresystem.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class OrderItemsResponseDto {

    @JsonProperty("laptop_id")
    private Long laptopId;

    private String model;

    @JsonProperty("purchase_amount")
    private Integer purchaseAmount;


    @JsonProperty("unit_price")
    private Double unitPrice;
}
