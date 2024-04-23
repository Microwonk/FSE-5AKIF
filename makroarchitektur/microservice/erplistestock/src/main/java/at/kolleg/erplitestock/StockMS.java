package at.kolleg.erplitestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StockMS {

    public static void main(String[] args) {
        SpringApplication.run(StockMS.class, args);
    }

}
