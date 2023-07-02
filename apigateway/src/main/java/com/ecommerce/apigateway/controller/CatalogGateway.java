package com.ecommerce.apigateway.controller;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogGateway {

    @Bean
    public RouteLocator catalogServices(RouteLocatorBuilder builder){
        return builder.routes()
                .route("PRODUCT-CATALOG-FETCH", r->r.path("/api/product/**")
                        .uri("http://localhost:8100/"))
                .build();
    }




}
