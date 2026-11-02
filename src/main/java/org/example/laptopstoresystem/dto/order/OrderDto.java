package org.example.laptopstoresystem.dto.order;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.example.laptopstoresystem.common.enums.OrderStatus;

import java.util.List;

@Data
public class OrderDto {


    @JsonProperty("items")
    @NotEmpty(message = "order item can not be empty")
    private List<OrderItemsDto> orderItems;

    private OrderStatus status = OrderStatus.PENDING;

}
