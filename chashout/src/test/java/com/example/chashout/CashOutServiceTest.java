package com.example.chashout;

import com.example.chashout.entity.CashOutEntity;
import com.example.chashout.model.CashOut;
import com.example.chashout.model.UserDto;
import com.example.chashout.repository.CashOutRepository;
import com.example.chashout.service.CashOutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CashOutServiceTest {

    @InjectMocks
    private CashOutService cashOutService;

    @Mock
    private CashOutRepository cashOutRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private CashOut cashOut;
    private CashOutEntity cashOutEntity;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cashOut = new CashOut();
        cashOut.setUserId(1L);
        cashOut.setAmount(50.0);

        cashOutEntity = new CashOutEntity();
        cashOutEntity.setUserId(1L);
        cashOutEntity.setAmount(50.0);

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setBalance(100.0);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void testCreateCashOutWithInsufficientBalance() {
        userDto.setBalance(30.0); // Balance menor que la cantidad solicitada para CashOut
        when(responseSpec.bodyToMono(UserDto.class)).thenReturn(Mono.just(userDto));

        Mono<CashOut> result = cashOutService.createCashOut(cashOut);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("Insufficient balance"))
                .verify();

        verify(cashOutRepository, never()).save(any(CashOutEntity.class));
    }

    @Test
    void testGetUserFromExternalApi() {
        when(responseSpec.bodyToMono(UserDto.class)).thenReturn(Mono.just(userDto));

        Mono<UserDto> result = cashOutService.getUserFromExternalApi(1L);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getId().equals(1L) && user.getBalance().equals(100.0))
                .verifyComplete();

        verify(webClientBuilder, times(1)).build();
    }



    @Test
    void testGetCashOutById() {
        when(cashOutRepository.findById(anyLong())).thenReturn(Mono.just(cashOutEntity));

        Mono<CashOut> result = cashOutService.getCashOut(1L);

        StepVerifier.create(result)
                .expectNextMatches(cashOut -> cashOut.getUserId().equals(1L) && cashOut.getAmount().equals(50.0))
                .verifyComplete();

        verify(cashOutRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetCashOutByUserId() {
        when(cashOutRepository.findByUserId(anyLong())).thenReturn(Flux.just(cashOutEntity));

       Flux<CashOut> result = cashOutService.getCashOutByUserId(1L);

        StepVerifier.create(result)
                .expectNextMatches(cashOut -> cashOut.getUserId().equals(1L) && cashOut.getAmount().equals(50.0))
                .verifyComplete();

        verify(cashOutRepository, times(1)).findByUserId(anyLong());
    }
}
