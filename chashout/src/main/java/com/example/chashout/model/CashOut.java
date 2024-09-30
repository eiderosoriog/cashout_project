package com.example.chashout.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class CashOut {
    private Long id;
    private Long userId;
    private Double amount;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

}