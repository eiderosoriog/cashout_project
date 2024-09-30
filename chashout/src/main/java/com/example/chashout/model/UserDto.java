package com.example.chashout.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Double balance;
    private Double amount;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
