package org.example.laptopstoresystem.service;


import jakarta.transaction.Transactional;
import org.example.laptopstoresystem.dto.order.OrderDto;
import org.example.laptopstoresystem.dto.order.OrderItemsDto;
import org.example.laptopstoresystem.dto.order.OrderResponseDto;
import org.example.laptopstoresystem.dto.order.OrderUpdateDto;
import org.example.laptopstoresystem.entity.Order;
import org.example.laptopstoresystem.entity.Stock;
import org.example.laptopstoresystem.exception.model.ResourceNotFoundException;
import org.example.laptopstoresystem.exception.model.UnprocessableEntityException;
import org.example.laptopstoresystem.mapper.OrderMapper;
import org.example.laptopstoresystem.repository.OrderRepository;
import org.example.laptopstoresystem.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;


    public List<OrderResponseDto> listOrders(){
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDtoList(orders);
    }



    @Transactional
    public void createOrder(OrderDto dto){
        // map for laptop ids
        List<Long> laptopIds = dto.getOrderItems().stream()
                .map(item -> item.getLaptopId())
                .toList();


        // get stock in laptopIds
        List<Stock> stocks = stockRepository.findByLaptopIdIn(laptopIds , Sort.by(Sort.Direction.ASC , "createdDate"));


        // map for required qty of laptopIds
        // ex: 1: 10 , 2: 14
        Map<Long , Integer> requiredQty = dto.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItemsDto::getLaptopId , OrderItemsDto::getAmount));



        // deduct stock for each product
        for (Long laptopId : requiredQty.keySet()){
            int remain = requiredQty.get(laptopId);


            // filter stock by laptopId
            List<Stock> stocksByLaptop = stocks.stream()
                    .filter(stock -> stock.getLaptop().getId().equals(laptopId))
                    .toList();


            // calculate and compare qty
            for (Stock stock : stocksByLaptop){
                if (remain <= 0) break;

                int available = stock.getStockQuantity();

                if (available >= remain){
                    stock.setStockQuantity(available - remain);
                    remain = 0;
                }else {
                    stock.setStockQuantity(0);
                    remain -= available;
                }
            }

            if (remain > 0){
                throw new UnprocessableEntityException("Not enough stock for laptop id :" + laptopId);
            }

            // save updated stocks to DB
            stockRepository.saveAll(stocks);
        }

        // create order entity
        Order order = orderMapper.toEntity(dto);
        orderRepository.save(order);
    }


    public void updateStatus(Long id , OrderUpdateDto dto){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("order not found with id : " + id));

        orderMapper.updateStatus(dto , order);
        orderRepository.save(order);
    }

    public void deleteOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("order not found with id : " + id));

        orderRepository.delete(order);
    }

}
