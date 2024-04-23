package com.example.erpliteapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiGateway {

    public static void main(String[] args) {
        SpringApplication.run(ApiGateway.class, args);
    }

    /* wurde durch Config in application.yml ersetzt
    @Bean
    RouteLocator gateway(RouteLocatorBuilder rlb)
    {
        //example Request: http://localhost:9999/api/v1/orders
        //example Request: http://localhost:9999/stock/packings
        return rlb.routes()
                .route(routeSpec -> routeSpec.path("/api/v1/**").uri("lb://erpliteorders"))
                .route(routeSpec -> routeSpec.path("/stock/**").uri("lb://erplitestock"))
                .build();
    }*/

}
