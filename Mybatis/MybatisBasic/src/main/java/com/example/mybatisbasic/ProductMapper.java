package com.example.mybatisbasic;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    void save(Product product);

    List<Product> findProducts(Integer page, Integer size, String name, Integer price);
}
