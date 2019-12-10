package com.qf.shop_bacck;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.qf")
@Import(FdfsClientConfig.class)
public class ShopBacckApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopBacckApplication.class, args);
    }

}
