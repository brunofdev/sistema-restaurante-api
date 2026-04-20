package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.validador.ProdutoValidador;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoJpaAdaptador;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AtualizarProdutosEmLoteCasoDeUso {

    private final ProdutoMapeador mapper;
    private final ProdutoJpaAdaptador repositorio;
    private final ProdutoValidador validador;
    private final ObterLoteDeProdutosPorIds obterLoteDeProdutosPorIds;

    public AtualizarProdutosEmLoteCasoDeUso(ProdutoMapeador mapper,
                                            ProdutoJpaAdaptador repositorio,
                                            ProdutoValidador validator,
                                            ObterLoteDeProdutosPorIds obterLoteDeProdutosPorIds) {
        this.mapper = mapper;
        this.repositorio = repositorio;
        this.validador = validator;
        this.obterLoteDeProdutosPorIds = obterLoteDeProdutosPorIds;
    }

    public List<ProdutoDTO> executar(List<ProdutoDTO> loteProdutosDTO){
        validador.validarListaDeProdutos(loteProdutosDTO);
        Map<Long, ProdutoDTO> mapaProdutosPorId = mapper.mapearIdsEntidadeParaDTO(loteProdutosDTO);
        List<Produto> produtosEncontrados = obterLoteDeProdutosPorIds.executar(mapaProdutosPorId.keySet());
        List<Produto>produtosAtualizados = mapper.atualizarProdutosEmLote(mapaProdutosPorId, produtosEncontrados);
        repositorio.saveAll(produtosAtualizados);
        return mapper.mapearListaDeEntidadeParaDTO(produtosAtualizados);
    }
}
