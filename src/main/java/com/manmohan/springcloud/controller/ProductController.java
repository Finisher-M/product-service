package com.manmohan.springcloud.controller;

import com.manmohan.springcloud.model.Product;
import com.manmohan.springcloud.repository.ProductRepo;
import com.manmohan.springcloud.restclients.CouponClient;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class ProductController {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CouponClient couponClient;

    @PostMapping("/products")
    @Retry(name="product-api" , fallbackMethod = "handleError")
    public Product create(@RequestBody Product product){
        var coupon=couponClient.getCoupon(product.getCouponCode());
        product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        return productRepo.save(product);
    }

    public Product handleError(Product product,Exception e){
        System.out.println("Inside Handle Error");
        return product;
    }
}
