package org.example.laptopstoresystem.mapper;


import org.apache.catalina.LifecycleState;
import org.example.laptopstoresystem.dto.stock.StockDto;
import org.example.laptopstoresystem.dto.stock.StockResponseDto;
import org.example.laptopstoresystem.entity.Laptop;
import org.example.laptopstoresystem.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {

    public Stock toEntity(StockDto dto , Laptop laptop){
        Stock stock = new Stock();
        stock.setStockQuantity(dto.getStockQuantity());
        stock.setLaptop(laptop);
        return stock;
    }


    public StockResponseDto toDto(Stock stock){
        StockResponseDto dto = new StockResponseDto();
        dto.setStockId(stock.getId());
        dto.setStockQuantity(stock.getStockQuantity());
        dto.setCreatedDate(stock.getCreatedDate());
        dto.setUpdatedDate(stock.getUpdatedDate());


        if (stock.getLaptop() != null){
            dto.setLaptopId(stock.getLaptop().getId());
        }

        return dto;
    }



    public List<StockResponseDto> toDtoList(List<Stock> stockList){
        if (stockList == null || stockList.isEmpty()){
            return new ArrayList<>();
        }

        return stockList.stream().map(this::toDto).collect(Collectors.toList());
    }
}