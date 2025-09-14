package com.anmi.productsservice.products.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private final static Logger LOGGER = LogManager.getLogger(ProductsController.class);

    @GetMapping
    public String getAllProducts(){
        LOGGER.info("Get all products");

        return "All products";
    }
}
