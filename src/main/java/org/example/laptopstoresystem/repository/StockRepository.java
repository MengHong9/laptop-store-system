package org.example.laptopstoresystem.repository;

import org.example.laptopstoresystem.entity.Stock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StockRepository extends JpaRepository<Stock , Long> {

    List<Stock> findByLaptopIdIn(List<Long> laptopId , Sort createAt);
}
