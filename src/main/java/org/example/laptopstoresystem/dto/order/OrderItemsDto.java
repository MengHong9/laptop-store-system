package org.example.laptopstoresystem.dto.order;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemsDto {

    @JsonProperty("laptop_id")
    @NotNull(message = "laptop_id is required")
    private Long laptopId;


    @NotNull(message = "amount is required")
    @Min(value = 1 , message = "amount must be at least 1")
    private Integer amount;
}
