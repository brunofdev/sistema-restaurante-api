package com.restaurante01.api_restaurante.core.mapper;

import java.util.List;
/**
*@param <Entity> Tipo da entidade
*@param <DTO> Tipo do DTO
*Esta implementação fornece as exigencias
*necessarias para a menor implementação correta de mappers
*/
public interface Mapper <Entity,DTO>{
    DTO mapearEntityParaDTO(Entity entity);
    List<DTO> mapearLoteParaDTO(List<Entity> loteEntitys);

}
