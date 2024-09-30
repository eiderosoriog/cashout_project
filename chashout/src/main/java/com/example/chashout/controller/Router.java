package com.example.chashout.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> routerUser(UserController controller) {
        return route(GET("/users/{id}"), controller::getUserById)
                .andRoute(POST("/users"), controller::createUser)
                .andRoute(PUT("/users/{id}/balance"), controller::updateUser);
    }

    @Bean
    public RouterFunction<ServerResponse> routerCashOut(CashOutController controller) {
        return route(GET("/cashouts/user/{id}"), controller::getByUserId)
                .andRoute(POST("/cashouts"), controller::createCashOut);
    }
}