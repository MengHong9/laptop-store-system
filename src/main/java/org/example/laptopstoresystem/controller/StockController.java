package org.example.laptopstoresystem.controller;


import jakarta.validation.Valid;
import org.example.laptopstoresystem.common.response.Response;
import org.example.laptopstoresystem.dto.stock.StockDto;
import org.example.laptopstoresystem.dto.stock.StockResponseDto;
import org.example.laptopstoresystem.dto.stock.StockUpdateDto;
import org.example.laptopstoresystem.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    @Autowired
    private StockService stockService;


    @GetMapping
    public ResponseEntity<Response> getAllStocks(){
        List<StockResponseDto> dtos = stockService.getAllStock();
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieved stock" , dtos));
    }


    @PostMapping
    public ResponseEntity<Response> addStock(@Valid @RequestBody StockDto dto){
        stockService.createStock(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("201" , "success" , "successfully created stock"));
    }


    @PutMapping("/{stockId}")
    public ResponseEntity<Response> updateStock(@PathVariable Long stockId , @Valid @RequestBody StockUpdateDto dto){
        stockService.updateStock(stockId , dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("200" , "success" , "successfully updated stock"));
    }



    @DeleteMapping("{stockId}")
    public ResponseEntity<Response> deleteStock(@PathVariable Long stockId){
        stockService.deleteStock(stockId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("200" , "success" , "successfully deleted stock"));
    }


    @GetMapping("{stockId}")
    public ResponseEntity<Response> getStockById(@PathVariable Long stockId){
        StockResponseDto dto = stockService.getStockById(stockId);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieved stock" , dto));
    }
}
