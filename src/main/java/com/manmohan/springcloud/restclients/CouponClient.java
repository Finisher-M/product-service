package com.manmohan.springcloud.restclients;

import com.manmohan.springcloud.model.Coupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("coupon-service")
public interface CouponClient {

    @GetMapping("/api/v1/coupons/{code}")
    Coupon getCoupon(@PathVariable("code") String code);
}
