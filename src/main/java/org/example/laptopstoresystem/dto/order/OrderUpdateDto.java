package org.example.laptopstoresystem.dto.order;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.laptopstoresystem.common.enums.OrderStatus;

@Data
public class OrderUpdateDto {

    @NotNull(message = "status is required")
    private OrderStatus status;
}
