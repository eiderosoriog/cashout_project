package com.example.chashout.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("cashout")
public class CashOutEntity {
    @Id
    @Column("id")
    private Long id;
    @Column("userId")
    private Long userId;
    @Column("amount")
    private Double amount;
    @Column("fecha_creacion")
    private LocalDateTime creationDate;
    @Column("fecha_modificacion")
    private LocalDateTime modificationDate;
}
