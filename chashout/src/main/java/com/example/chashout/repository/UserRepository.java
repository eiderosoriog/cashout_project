package com.example.chashout.repository;

import com.example.chashout.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {


}