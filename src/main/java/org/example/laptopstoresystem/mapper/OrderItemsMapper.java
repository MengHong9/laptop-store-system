package org.example.laptopstoresystem.mapper;


import org.example.laptopstoresystem.dto.order.OrderItemsDto;

import org.example.laptopstoresystem.dto.order.OrderItemsResponseDto;
import org.example.laptopstoresystem.entity.Laptop;
import org.example.laptopstoresystem.entity.OrderItem;
import org.example.laptopstoresystem.exception.model.ResourceNotFoundException;
import org.example.laptopstoresystem.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemsMapper {

    @Autowired
    private LaptopRepository laptopRepository;

    public OrderItem toEntity(OrderItemsDto dto){
        OrderItem orderItem = new OrderItem();
        orderItem.setLaptopId(dto.getLaptopId());
        orderItem.setQuantity(dto.getAmount());


        return orderItem;
    }

    public OrderItemsResponseDto toDto(OrderItem orderItem){
        if (orderItem == null){
            return null;
        }

        OrderItemsResponseDto dto = new OrderItemsResponseDto();
        dto.setLaptopId(orderItem.getLaptopId());
        dto.setPurchaseAmount(orderItem.getQuantity());


        // query get Laptop
        Laptop laptop = laptopRepository.findById(orderItem.getLaptopId())
                .orElseThrow(() -> new ResourceNotFoundException("laptop not found with id : " + orderItem.getLaptopId()));

        dto.setModel(laptop.getModel());
        dto.setUnitPrice(laptop.getPrice());


        return dto;
    }

    public List<OrderItemsResponseDto> toDtoList(List<OrderItem> entities){
        return entities.stream().map(orderItem -> toDto(orderItem))
                .toList();
    }
}
