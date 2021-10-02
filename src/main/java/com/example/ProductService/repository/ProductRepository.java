package com.example.ProductService.repository;
import java.util.List;

import com.example.ProductService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByType(String type);
    List<Product> findByColor(String color);
    List<Product> findByColorAndType(String color, String type);

    /*
    Now we can use JpaRepository’s methods: save(), findOne(), findById(), findAll(), count(), delete(), deleteById()… without implementing these methods.
     */
}
