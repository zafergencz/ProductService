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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Api(value = "User Api documentation")
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    ProductController(){
        logger.info("ProductController");
    }

    @GetMapping("/products")
    @ApiOperation(value = "Get Products By Type Or Color")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) String type,@RequestParam(required = false) String color) {
        logger.info("getProducts type=" + type + " color=" + color);

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
            logger.error("getProducts ex: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    @ApiOperation(value = "Get Products By Unique Id")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        try{
            logger.info("getProductById id=" + id);
            Product product = productRepository.findById(id).get();
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (Exception e){
            logger.error("getProductById ex=", e.getMessage());
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
            logger.error("createProduct ex=" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("products/{id}")
    @ApiOperation(value = "Delete A Product By Id")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable long id){
        try {
            logger.info("deleteProduct");
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("deleteProduct ex=" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products")
    @ApiOperation(value = "Delete All Products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            logger.info("deleteAllProducts");
            productRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("deleteAllProducts ex=" + e.getMessage());
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
            logger.warn("updateProduct content not found!" );
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
