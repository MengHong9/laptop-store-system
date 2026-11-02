package org.example.laptopstoresystem.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.laptopstoresystem.common.enums.OrderStatus;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {

    @JsonProperty("order_id")
    private Long id;


    private OrderStatus status;


    @JsonProperty("total_price")
    private Double totalPrice;


    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;


    @JsonProperty("items")
    private List<OrderItemsResponseDto> items;
}
