package org.example.laptopstoresystem.repository;




import org.example.laptopstoresystem.entity.Laptop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    @Query("select distinct lap from Laptop lap left join fetch lap.stocks")
    List<Laptop> findAllWithStocks();

    @Query("select lap from Laptop lap where " +
            "(:model is null or lower(lap.model) like %:model%) and" +
            "(:maxPrice is null or lap.price <= :maxPrice) and" +
            "(:minPrice is null or lap.price >= :minPrice)"
    )
    List<Laptop> findLaptopByFilter(@Param("model") String model , @Param("maxPrice") Double maxPrice , @Param("minPrice") Double minPrice);

}


//@Query("SELECT p FROM Product p WHERE "+
//        "(:name IS NULL OR LOWER(p.productName) LIKE %:name%) AND"+
//        "(:maxPrice IS NULL OR p.price <= :maxPrice) AND"+
//        "(:minPrice IS NULL OR p.price >= :minPrice)"
//)
//List<Product> findProductWithFilter(
//        @Param("name") String name,
//        @Param("maxPrice") Double maxPrice,
//        @Param("minPrice") Double minPrice
//);
