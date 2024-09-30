package com.example.chashout.controller;


import com.example.chashout.model.CashOut;
import com.example.chashout.service.interfaces.IntCashOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CashOutController {

    @Autowired
    private IntCashOutService cashOutService;

    public Mono<ServerResponse> getByUserId(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
                cashOutService.getCashOutByUserId(Long.parseLong(serverRequest.pathVariable("id"))),
                CashOut.class);
    }


    public Mono<ServerResponse> createCashOut(ServerRequest serverRequest) {
        Mono<CashOut> cashOutMono = serverRequest.bodyToMono(CashOut.class);
        return cashOutMono.flatMap(cashOut->
                this.cashOutService.createCashOut(cashOut)
                .flatMap(x -> ServerResponse.ok().body(BodyInserters.fromValue(x)))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage())));
    }

}