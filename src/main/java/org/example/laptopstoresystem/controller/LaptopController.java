package org.example.laptopstoresystem.controller;


import jakarta.validation.Valid;
import org.example.laptopstoresystem.common.response.Response;
import org.example.laptopstoresystem.dto.laptop.LaptopDto;
import org.example.laptopstoresystem.dto.laptop.LaptopResponseDto;
import org.example.laptopstoresystem.dto.laptop.LaptopUpdateDto;
import org.example.laptopstoresystem.entity.Laptop;
import org.example.laptopstoresystem.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/laptops")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;


    @GetMapping
    public ResponseEntity<Response> getAllLaptop(){
        List<LaptopResponseDto> laptops = laptopService.getAllLaptop();
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieved laptop" , laptops));
    }


    @PostMapping
    public ResponseEntity<Response> addLaptop(@Valid @RequestBody LaptopDto dto){
        laptopService.createLaptop(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("201" , "success" , "successfully created data"));
    }


    @PutMapping("{id}")
    public ResponseEntity<Response> updateLaptop(@PathVariable Long id , @Valid @RequestBody LaptopUpdateDto dto){
        laptopService.updateLaptop(id , dto);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully updated laptop"));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteLaptop(@PathVariable Long id){
        laptopService.deleteLaptop(id);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully deleted laptop"));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getLaptopById(@PathVariable Long id){
        LaptopResponseDto dto = laptopService.getLaptopById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully get laptop by id" , dto));
    }


    @GetMapping("/search")
    public ResponseEntity<Response> getLaptopByFilter(
            @RequestParam(value = "model" , required = false) String model,
            @RequestParam(value = "maxPrice" , required = false) Double maxPrice,
            @RequestParam(value = "minPrice" ,required = false) Double minPrice
    ){
        List<Laptop> laptops = laptopService.getLaptopByFilter(model , maxPrice , minPrice);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully get laptop by filter" , laptops));
    }



}
