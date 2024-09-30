package com.example.chashout.repository;

import com.example.chashout.entity.CashOutEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CashOutRepository extends ReactiveCrudRepository<CashOutEntity, Long> {


    @Query("SELECT * FROM cashout WHERE userId = :userId")
    Flux<CashOutEntity> findByUserId(Long userId);
}