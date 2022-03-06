package com.example.mybatisbasic;


public class Product {
    private Long id;

    private String name;

    private String explanation;

    private Long price;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExplanation() {
        return explanation;
    }

    public Long getPrice() {
        return price;
    }
    public Product(Long id, String name, String explanation, Long price) {
        this.id = id;
        this.name = name;
        this.explanation = explanation;
        this.price = price;
    }
}
