package org.example.laptopstoresystem.mapper;


import org.example.laptopstoresystem.dto.order.*;
import org.example.laptopstoresystem.entity.Order;
import org.example.laptopstoresystem.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    public Order toEntity(OrderDto dto) {
        Order order = new Order();

        List<OrderItem> orderItems = dto.getOrderItems()
                .stream()
                .map(orderItemsDto -> {
                    OrderItem orderItem = orderItemsMapper.toEntity(orderItemsDto);

                    orderItem.setOrder(order);

                    return orderItem;
                })
                .toList();

        order.setItems(orderItems);
        order.setStatus(dto.getStatus());


        return order;
    }

    public OrderResponseDto toDto(Order order){
         if (order == null){
             return null;
         }

         OrderResponseDto dto = new OrderResponseDto();
         dto.setId(order.getId());
         dto.setStatus(order.getStatus());
         dto.setCreatedAt(order.getCreated_at());
         dto.setUpdatedAt(order.getUpdated_at());

        if (order.getItems() != null && !order.getItems().isEmpty()){
            List<OrderItemsResponseDto> orderitensDtos = orderItemsMapper.toDtoList(order.getItems());

            dto.setItems(orderitensDtos);

            // map for total price
            Double total =  calculateTotalPrice(orderitensDtos);

            dto.setTotalPrice(total);
        }
        return dto;
    }


    public List<OrderResponseDto> toDtoList(List<Order> entities){
        if (entities == null || entities.isEmpty()){
            return new ArrayList<>();
        }

        return entities.stream().map(order -> toDto(order)).collect(Collectors.toList());
    }


    public void updateStatus(OrderUpdateDto dto , Order order){
        if (order == null || dto == null){
            return;
        }

        order.setStatus(dto.getStatus());
    }


    private Double calculateTotalPrice(List<OrderItemsResponseDto> itemsResponseDtos){
        return itemsResponseDtos
                .stream()
                .mapToDouble(orderItemsResponseDto -> orderItemsResponseDto.getPurchaseAmount() * orderItemsResponseDto.getUnitPrice()).sum();

    }

}
