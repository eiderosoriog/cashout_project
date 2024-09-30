package com.example.chashout.controller;


import com.example.chashout.model.UserDto;
import com.example.chashout.service.interfaces.IntUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private IntUserService userService;

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        return userService.getUserById(Long.parseLong(serverRequest.pathVariable("id")))
                .flatMap(x -> ServerResponse.ok().body(BodyInserters.fromValue(x)))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error));
    }


    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        Mono<UserDto> user = serverRequest.bodyToMono(UserDto.class);
        return this.userService.createUser(user)
                .flatMap(x -> ServerResponse.ok().body(BodyInserters.fromValue(x)))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        Mono<UserDto> user = serverRequest.bodyToMono(UserDto.class);
        return this.userService.updateBalance(user,Long.parseLong(serverRequest.pathVariable("id")))
                .flatMap(x -> ServerResponse.ok().body(BodyInserters.fromValue(x)))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }


/*    @PostMapping("users/{id}")
    public Mono<ResponseEntity<UserDto>> transferMoney(@RequestBody TransferRequest request) {
        return userService.getUserById(1L)
                .map(successMessage -> ResponseEntity.ok(successMessage)) ; // Retorna mensaje de éxito
    }*/


/*
    @PostMapping("/accounts/transfer")
    public Mono<ResponseEntity<String>> transferMoney(@RequestBody TransferRequest request) {
        return bankService.transferMoney(request)
                .map(successMessage -> ResponseEntity.ok(successMessage))  // Retorna mensaje de éxito
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body("Error: " + e.getMessage())));
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public Flux<Transaction> getTransactions(@PathVariable String accountId) {
        return bankService.getTransactions(accountId);
    }

    @PostMapping("/accounts/create")
    public Mono<String> createAccount(@RequestBody CreateAccountRequest request) {
        return bankService.createAccount(request);
    }

    @DeleteMapping("/accounts/{accountId}")
    public Mono<String> closeAccount(@PathVariable String accountId) {
        return bankService.closeAccount(accountId);
    }

    @PutMapping("/accounts/update")
    public Mono<String> updateAccount(@RequestBody UpdateAccountRequest request) {
        return bankService.updateAccount(request);
    }

    @GetMapping("/accounts/{accountId}/profile")
    public Mono<CustomerProfile> getCustomerProfile(@PathVariable String accountId) {
        return bankService.getCustomerProfile(accountId);
    }

    @GetMapping("/customers/{customerId}/loans")
    public Flux<Loan> getActiveLoans(@PathVariable String customerId) {
        return bankService.getActiveLoans(customerId);
    }

    @GetMapping("/accounts/{accountId}/interest")
    public Flux<Double> simulateInterest(@PathVariable String accountId) {
        return bankService.simulateInterest(accountId);
    }

    @GetMapping("/loans/{loanId}")
    public Mono<String> getLoanStatus(@PathVariable String loanId) {
        return bankService.getLoanStatus(loanId);
    }
*/

}