package com.example.chashout.service.interfaces;

import com.example.chashout.model.CashOut;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IntCashOutService {

    Mono<CashOut> createCashOut(CashOut cashOut);
    Mono<CashOut> getCashOut(Long id);
    Flux<CashOut> getCashOutByUserId(Long userId);

}
