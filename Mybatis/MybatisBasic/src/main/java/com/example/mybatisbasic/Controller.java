package com.example.mybatisbasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/save")
    public void saveController() {
        Product product1 = new Product(1L,"product1","hello1",12000L);
        Product product2 = new Product(2L,"product2","hello2",15000L);
        Product product3 = new Product(3L,"product3","hello3",18000L);


        productMapper.save(product1);
        productMapper.save(product2);
        productMapper.save(product3);
    }

    @GetMapping("/find")
    //http://localhost:8080/find?page=0&size=1
    //http://localhost:8080/find?page=0&size=1&name=product1
    //http://localhost:8080/find?page=0&size=1&price=15000
    //http://localhost:8080/find?page=0&size=1&name=product3&price=18000
    public void findController(Integer page, Integer size, String name, Integer price) {
        List<Product> products = productMapper.findProducts(page, size, name, price);
        for (Product p : products) {
            System.out.println(p.getName());
            System.out.println(p.getPrice());
        }
        System.out.println("-----------------------");
    }
}
