package com.restaurante01.api_restaurante.core.mapper;

import java.util.List;

public abstract class AbstractMapper<Entity, DTO> implements Mapper<Entity, DTO> {

    @Override
    public abstract DTO mapearUmaEntidadeParaDTO(Entity entidade);

    @Override
    public List<DTO> mapearListaDeEntidadeParaDTO(List<Entity> listaEntidade) {
        return listaEntidade.stream()
                .map(this::mapearUmaEntidadeParaDTO) // delega a conversão individual para a subclasse
                .toList();
    }

    @Override
    public abstract Entity mapearUmaDtoParaEntidade(DTO dto);

    @Override
    public List<Entity> mapearListaDeDtoParaEntidade(List<DTO> listaDto) {
        return listaDto.stream()
                .map(this::mapearUmaDtoParaEntidade) // delega a conversão individual para a subclasse
                .toList();
    }
}
