package com.example.chashout.service;

import com.example.chashout.config.Mapper;
import com.example.chashout.entity.UserEntity;
import com.example.chashout.model.UserDto;
import com.example.chashout.repository.UserRepository;
import com.example.chashout.service.interfaces.IntUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService implements IntUserService {

    @Autowired
    private final UserRepository repository;


    @Override
    public Mono<UserDto> getUserById(Long id) {
        return repository.findById(id)
                .map(x -> Mapper.toDto(x, UserDto.class))
                ;
    }


    @Override
    public Mono<UserDto> createUser(Mono<UserDto> userMono) {
        return userMono.flatMap(user -> {
            user.setCreationDate(LocalDateTime.now());
            var userEntity = Mapper.toEntity(user, UserEntity.class);
            return repository.save(userEntity)
                    .map(x -> Mapper.toDto(x, UserDto.class));
        });
    }

    @Override
    public Mono<UserDto> updateBalance(Mono<UserDto> userMono, Long Id) {
        return userMono.flatMap(user ->
                repository.findById(Id).flatMap(userEntity ->
                        {
                            userEntity.setBalance(userEntity.getBalance() + user.getAmount());
                            userEntity.setModificationDate(LocalDateTime.now());
                            return repository.save(userEntity)
                                    .map(x -> Mapper.toDto(x, UserDto.class));
                        }
                ));
    }

}
