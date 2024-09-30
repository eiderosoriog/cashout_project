package com.example.chashout.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("usuario")
public class UserEntity {
    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("balance")
    private Double balance;
    @Column("fecha_creacion")
    private LocalDateTime creationDate;
    @Column("fecha_modificacion")
    private LocalDateTime modificationDate;
}
