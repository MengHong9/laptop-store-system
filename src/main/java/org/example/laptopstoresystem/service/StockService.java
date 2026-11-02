package org.example.laptopstoresystem.service;


import org.example.laptopstoresystem.dto.stock.StockDto;
import org.example.laptopstoresystem.dto.stock.StockResponseDto;
import org.example.laptopstoresystem.dto.stock.StockUpdateDto;
import org.example.laptopstoresystem.entity.Laptop;
import org.example.laptopstoresystem.entity.Stock;
import org.example.laptopstoresystem.exception.model.ResourceNotFoundException;
import org.example.laptopstoresystem.exception.model.UnprocessableEntityException;
import org.example.laptopstoresystem.mapper.StockMapper;
import org.example.laptopstoresystem.repository.LaptopRepository;
import org.example.laptopstoresystem.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {


    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;


    @Autowired
    private LaptopRepository laptopRepository;


    public List<StockResponseDto> getAllStock(){
        List<Stock> dtos = stockRepository.findAll();
        return stockMapper.toDtoList(dtos);
    }


    public void createStock(StockDto dto){
        Laptop laptop = laptopRepository.findById(dto.getLaptop_id())
                .orElseThrow(() -> new ResourceNotFoundException("laptop id not found with id " + dto.getLaptop_id()));

        Stock stock = stockMapper.toEntity(dto , laptop);
        stockRepository.save(stock);
    }


    public void updateStock(Long id  , StockUpdateDto updateDto){
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found"));


        if (updateDto.getOperationTypes() == 1){
            int newStockQuantity = stock.getStockQuantity() + updateDto.getStockQuantity();
            stock.setStockQuantity(newStockQuantity);
        } else if (updateDto.getOperationTypes() == 2) {
            if (stock.getStockQuantity() < updateDto.getStockQuantity()){
                throw new UnprocessableEntityException("quantity to remove can not be exceeded than existing stock");
            }
            int newStock = stock.getStockQuantity() - updateDto.getStockQuantity();
            stock.setStockQuantity(newStock);
        }else {
            throw new IllegalArgumentException("Invalid operation type");
        }

        stockRepository.save(stock); 

    }


    public void deleteStock(Long id){
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id not found"));

        stockRepository.delete(stock);
    }



    public StockResponseDto getStockById(Long id){
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id not found"));

        return stockMapper.toDto(stock);
    }




}