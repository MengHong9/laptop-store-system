package org.example.laptopstoresystem.controller;



import jakarta.validation.Valid;
import org.example.laptopstoresystem.common.response.Response;
import org.example.laptopstoresystem.dto.order.OrderDto;
import org.example.laptopstoresystem.dto.order.OrderResponseDto;
import org.example.laptopstoresystem.dto.order.OrderUpdateDto;
import org.example.laptopstoresystem.entity.Order;
import org.example.laptopstoresystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;



    @GetMapping
    public ResponseEntity<Response> listOrders(){
        List<OrderResponseDto> dtos = orderService.listOrders();
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully list orders" , dtos));
    }

    @PostMapping
    public ResponseEntity<Response> placeOrder(@Valid @RequestBody OrderDto dto){
        orderService.createOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("201" , "success" , "successfully created order"));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateStatus(@PathVariable Long id , @Valid @RequestBody OrderUpdateDto dto){
        orderService.updateStatus(id , dto);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully updated status"));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully deleted order"));
    }
}
