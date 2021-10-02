package com.example.ProductService;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class ProductController {
    private final AtomicLong counter = new AtomicLong();
    ArrayList<ProductEntity> productEntityList = new ArrayList<>();

    ProductController(){
    }

    @GetMapping("/products")
    public ArrayList<ProductEntity> products(@RequestParam(defaultValue = "*") String type,@RequestParam(defaultValue = "*") String color) {

        List<ProductEntity> tmpList;
        if(color.equals("*") && type.equals("*")) {
            return productEntityList;
        }
        else if(!color.equals("*") && type.equals("*")){
            tmpList = productEntityList.stream().filter(x -> x.color.equals(color)).collect(Collectors.toList());
        }
        else if(color.equals("*") && !type.equals("*")){
            tmpList = productEntityList.stream().filter(x -> x.type.equals(type)).collect(Collectors.toList());
        }
        else{
            tmpList = productEntityList.stream().filter(x -> x.type.equals(type) && x.color.equals(color)).collect(Collectors.toList());
        }

        return new ArrayList<>(tmpList);
    }
    @GetMapping("/products/{id}")
    public ArrayList<ProductEntity> products(@PathVariable long id) {

        return new ArrayList<>(productEntityList.stream().filter(x -> x.id == id).collect(Collectors.toList()));
    }

    @PostMapping(path = "products")
    public String create(@RequestBody ProductEntity entity){
        entity.id = counter.incrementAndGet();
        entity.createDate = new Date();
        productEntityList.add(entity);
        return  "OK";
    }

    @DeleteMapping("products/{id}")
    public String delete(@PathVariable long id){
        productEntityList.removeIf((x) -> (x.id == id));
        return "OK";
    }

    @PutMapping("/products/{id}")
    public String put(@RequestBody ProductEntity entity, @PathVariable long id){

        productEntityList.forEach(x -> {

            if(x.id == id){
                x.createDate = new Date();
                x.type = entity.type;
                x.name = entity.name;
                x.color = entity.color;
                return;
            }
        });
        return "OK";
    }
}
