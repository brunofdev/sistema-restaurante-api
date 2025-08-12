package com.restaurante01.api_restaurante.core.mapper;

import java.util.List;
/**
 * Implementação abstrata genérica da interface Mapper.
 *
 * Esta classe fornece uma implementação padrão para o mapeamento
 * de listas de entidades para listas de DTOs, usando o método abstrato
 * {@link #mapearEntityParaDTO(Entity)} que deve ser implementado
 * pelas subclasses específicas para cada tipo de entidade.
 *
 * @param <Entity> Tipo da entidade do domínio
 * @param <DTO>    Tipo do DTO correspondente
 */
public abstract class AbstractMapper<Entity, DTO> implements Mapper<Entity, DTO> {
    /**
     * Implementação padrão para mapear uma lista de entidades para DTOs,
     * utilizando stream e o método {@link #mapearEntityParaDTO(Entity)}.
     * @param entities Lista de entidades a serem convertidas
     * @return Lista de DTOs correspondentes
     */
    @Override
    public List<DTO> mapearLoteParaDTO(List<Entity> entities) {
        return entities.stream()
                .map(this::mapearEntityParaDTO) // delega a conversão individual para a subclasse
                .toList();
    }

    /**
     * Método abstrato que deve ser implementado para converter
     * uma única entidade em seu DTO correspondente.
     * @param entity Entidade a ser convertida
     * @return DTO equivalente à entidade
     */
    @Override
    public abstract DTO mapearEntityParaDTO(Entity entity);
}
