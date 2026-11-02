package org.example.laptopstoresystem.service;


import org.example.laptopstoresystem.dto.laptop.LaptopDto;
import org.example.laptopstoresystem.dto.laptop.LaptopResponseDto;
import org.example.laptopstoresystem.dto.laptop.LaptopUpdateDto;
import org.example.laptopstoresystem.entity.Laptop;
import org.example.laptopstoresystem.exception.model.ResourceNotFoundException;
import org.example.laptopstoresystem.mapper.LaptopMapper;
import org.example.laptopstoresystem.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private LaptopMapper laptopMapper;


    public List<LaptopResponseDto> getAllLaptop(){
        List<Laptop> laptops = laptopRepository.findAllWithStocks();
        return laptopMapper.toDtoList(laptops);
    }


    public void createLaptop(LaptopDto dto){
        Laptop laptop = laptopMapper.toEntity(dto);
        laptopRepository.save(laptop);
    }


    public void updateLaptop(Long id , LaptopUpdateDto dto){
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("laptop not found"));

        laptopMapper.updateLaptop(laptop , dto);
        laptopRepository.save(laptop);
    }

    public void deleteLaptop(Long id){
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("laptop not found"));

        laptopRepository.delete(laptop);
    }


    public LaptopResponseDto getLaptopById(Long id){
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("laptop not found"));

        return laptopMapper.toDto(laptop);
    }


    public List<Laptop> getLaptopByFilter(String model , Double maxPrice , Double minPrice){
        String formatModel = model != null ? model.toLowerCase() : null;
        return laptopRepository.findLaptopByFilter(formatModel , maxPrice , minPrice);
    }


}
