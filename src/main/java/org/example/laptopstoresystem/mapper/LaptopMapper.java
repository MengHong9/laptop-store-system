package org.example.laptopstoresystem.mapper;


import org.example.laptopstoresystem.dto.laptop.LaptopDto;
import org.example.laptopstoresystem.dto.laptop.LaptopResponseDto;
import org.example.laptopstoresystem.dto.laptop.LaptopUpdateDto;
import org.example.laptopstoresystem.entity.Laptop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LaptopMapper {


    public Laptop toEntity(LaptopDto dto){
        Laptop laptop = new Laptop();
        laptop.setModel(dto.getModel());
        laptop.setCpu(dto.getCpu());
        laptop.setRam(dto.getRam());
        laptop.setStorage(dto.getStorage());
        laptop.setGpu(dto.getGpu());
        laptop.setPrice(dto.getPrice());

        return laptop;
    }

    public LaptopResponseDto toDto(Laptop laptop){
        LaptopResponseDto dto = new LaptopResponseDto();
        dto.setId(laptop.getId());
        dto.setModel(laptop.getModel());
        dto.setCpu(laptop.getCpu());
        dto.setRam(laptop.getRam());
        dto.setStorage(laptop.getStorage());
        dto.setGpu(laptop.getGpu());
        dto.setPrice(laptop.getPrice());
        dto.setTotalStock(laptop.getTotalStocks());
        dto.setCreated_at(laptop.getCreated_at());
        dto.setUpdated_at(laptop.getUpdated_at());

        return dto;
    }


    public List<LaptopResponseDto> toDtoList(List<Laptop> entities){
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream().map(laptop -> this.toDto(laptop)).collect(Collectors.toList());
    }
    
    public void updateLaptop(Laptop laptop ,LaptopUpdateDto dto){

        laptop.setModel(dto.getModel());
        laptop.setCpu(dto.getCpu());
        laptop.setRam(dto.getRam());
        laptop.setStorage(dto.getStorage());
        laptop.setGpu(dto.getGpu());
        laptop.setPrice(dto.getPrice());
    }

}
