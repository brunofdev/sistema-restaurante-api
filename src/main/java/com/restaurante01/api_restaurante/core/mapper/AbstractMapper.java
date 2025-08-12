package com.restaurante01.api_restaurante.core.mapper;

import java.util.List;

public abstract class AbstractMapper<Entity, DTO> implements Mapper<Entity, DTO> {
    @Override
    public List<DTO> mapearLoteParaDTO(List<Entity> entities){
        return entities.stream()
                .map(this::mapearEntityParaDTO)
                .toList();
    }
    @Override
    public abstract DTO mapearEntityParaDTO (Entity entity);

}

