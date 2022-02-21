package com.netcracker.game.services.mapstruct;

import java.util.List;

public abstract class Mapper<E, D> {

    public abstract E toEntity(D dto);
    public abstract List<E> toEntity(Iterable<D> dtos);
    public abstract D toDto(E entity);
    public abstract List<D> toDto(Iterable<E> entities);
}
