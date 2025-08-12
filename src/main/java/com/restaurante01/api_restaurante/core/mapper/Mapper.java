package com.restaurante01.api_restaurante.core.mapper;

import java.util.List;

/**
 * Interface genérica para mapeamento entre entidade e DTO.
 *
 * @param <Entity> Tipo da entidade do domínio (ex: Produto, Cardapio)
 * @param <DTO>    Tipo do DTO correspondente usado na camada de apresentação ou transferência
 *
 * Esta interface define o contrato mínimo para classes que fazem
 * a conversão de objetos entidade para objetos DTO e vice-versa,
 * incluindo métodos para mapeamento individual e em lote.
 */
public interface Mapper<Entity, DTO> {

    /**
     * Converte uma entidade do domínio para o seu DTO correspondente.
     * @param entity Objeto entidade a ser convertido
     * @return Objeto DTO equivalente à entidade fornecida
     */
    DTO mapearEntityParaDTO(Entity entity);

    /**
     * Converte uma lista de entidades do domínio para uma lista de DTOs correspondentes.
     * @param loteEntitys Lista de entidades a serem convertidas
     * @return Lista de DTOs correspondentes
     */
    List<DTO> mapearLoteParaDTO(List<Entity> loteEntitys);
}
