package com.example.chashout.service.interfaces;

import com.example.chashout.model.UserDto;
import reactor.core.publisher.Mono;

public interface IntUserService {

    Mono<UserDto> getUserById(Long id);
    Mono<UserDto> createUser(Mono<UserDto> userMono);
    Mono<UserDto> updateBalance(Mono<UserDto> userMono, Long id);

}
