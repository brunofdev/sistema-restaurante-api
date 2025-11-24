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
     * @param entidade Objeto entidade a ser convertido
     * @return Objeto DTO equivalente à entidade fornecida
     */
    DTO mapearUmaEntidadeParaDTO(Entity entidade);

    /**
     * Converte uma lista de entidades do domínio para uma lista de DTOs correspondentes.
     * @param listaEntidade Lista de entidades a serem convertidas
     * @return Lista de DTOs correspondentes
     */
    List<DTO> mapearListaDeEntidadeParaDTO(List<Entity> listaEntidade);

    /**
     * Converte um DTO de transferência de dados para a sua entidade de domínio correspondente.
     * Este método é útil para operações de persistência, onde o DTO recebido precisa ser
     * convertido em uma entidade antes de ser salvo no banco de dados.
     *
     * @param dto Objeto DTO a ser convertido.
     * @return Objeto Entity equivalente ao DTO fornecido.
     */
    Entity mapearUmaDtoParaEntidade(DTO dto);

    /**
     * Converte uma lista de DTOs de transferência de dados para uma lista de entidades de domínio correspondentes.
     * Este método facilita a conversão em lote, otimizando operações de persistência
     * ou processamento de coleções de dados recebidas.
     *
     * @param listaDTO Lista de DTOs a serem convertidos.
     * @return Lista de Entities correspondentes aos DTOs fornecidos.
     */
    List<Entity> mapearListaDeDtoParaEntidade(List<DTO> listaDTO);

}
