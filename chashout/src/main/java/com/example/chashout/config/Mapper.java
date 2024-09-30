package com.example.chashout.config;

import org.springframework.beans.BeanUtils;

public class Mapper {
    public static <D, E> D toDto(E entity, Class<D> dtoClass) {
        D dto = BeanUtils.instantiateClass(dtoClass);
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static <D, E> E toEntity(D dto, Class<E> entityClass) {
        E entity = BeanUtils.instantiateClass(entityClass);
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}