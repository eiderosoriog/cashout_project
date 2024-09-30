package com.example.chashout.service;


import com.example.chashout.config.Mapper;
import com.example.chashout.entity.CashOutEntity;
import com.example.chashout.model.CashOut;
import com.example.chashout.model.UserDto;
import com.example.chashout.repository.CashOutRepository;
import com.example.chashout.service.interfaces.IntCashOutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
@AllArgsConstructor
public class CashOutService implements IntCashOutService {

    private final CashOutRepository cashOutRepository;

    private final WebClient.Builder webClientBuilder; // Inyecta WebClient.Builder para construir el cliente HTTP

    private static final String BASE_URL = "http://localhost:8080/users";


    public Mono<CashOut> createCashOut(CashOut cashOut) {
        return getUserFromExternalApi(cashOut.getUserId())
                .flatMap(user->{
                    if (user.getBalance().compareTo(cashOut.getAmount()) < 0) {
                        return Mono.error(new IllegalArgumentException("Insufficient balance"));
                    }else{
                        user.setAmount(-cashOut.getAmount());
                        return updateUserBalanceViaApi(user)
                                .then(cashOutRepository.save(Mapper.toDto(cashOut, CashOutEntity.class)))
                                .map(savedEntity -> Mapper.toDto(savedEntity, CashOut.class));                    }
                })
                .flatMap(user->
                        cashOutRepository.save(Mapper.toDto(cashOut, CashOutEntity.class))
                                .map(x -> Mapper.toDto(x, CashOut.class))
                )
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Error creating cashOut")));
    }

    public Mono<UserDto> getUserFromExternalApi(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri(BASE_URL+"/{id}", userId)
                .retrieve()
                .bodyToMono(UserDto.class); // User es un modelo que representa al usuario
    }

    private Mono<Void> updateUserBalanceViaApi(UserDto user) {
        return webClientBuilder.build()
                .put()
                .uri(BASE_URL+"/{id}/balance", user.getId())
                .bodyValue(user)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<CashOut> getCashOut(Long id) {
        return cashOutRepository.findById(id)
                .map(x -> Mapper.toDto(x, CashOut.class));
    }

    public Flux<CashOut> getCashOutByUserId(Long userId) {
        return cashOutRepository.findByUserId(userId)
                .map(x -> Mapper.toDto(x, CashOut.class));
    }
}