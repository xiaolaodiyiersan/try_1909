package com.qf.shop_search;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
public class ShopSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSearchApplication.class, args);
    }

}
