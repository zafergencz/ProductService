package com.example.ProductService;
import java.util.*;

import com.example.ProductService.model.Product;
import com.example.ProductService.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

@Api(value = "User Api documentation")
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    ProductController(){
    }

    @GetMapping("/products")
    @ApiOperation(value = "Get Products By Type Or Color")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) String type,@RequestParam(required = false) String color) {

        try {
            List<Product> products = new ArrayList<>();
            if(color == null && type == null){
                productRepository.findAll().forEach(products::add);
            }else if(color != null && type == null){
                productRepository.findByColor(color).forEach(products::add);
            }else if(color == null && type != null){
                productRepository.findByType(type).forEach(products::add);
            }else{
                productRepository.findByColorAndType(color, type).forEach(products::add);
            }

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    @ApiOperation(value = "Get Products By Unique Id")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        try{
            Product product = productRepository.findById(id).get();
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "products")
    @ApiOperation(value = "Create A New Product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        try{
            Product _product = productRepository
                    .save(new Product(product.getName(), product.getDetail(), product.getType(), product.getColor(), new Date(), new Date()));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("products/{id}")
    @ApiOperation(value = "Delete A Product By Id")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable long id){
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products")
    @ApiOperation(value = "Delete All Products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            productRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/products/{id}")
    @ApiOperation(value = "Update A Specific Product Using Id")
    public ResponseEntity<Product> updateProduct(@RequestBody Product entity, @PathVariable long id){

        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product _product = productData.get();
            _product.setName(entity.getName());
            _product.setDetail(entity.getDetail());
            _product.setType(entity.getType());
            _product.setColor(entity.getColor());
            _product.setUpdateDate(new Date());

            return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
