package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapper;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.validador.ProdutoValidator;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.ProdutoRepositorioAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AtualizarProdutosEmLoteCasoDeUso {

    private final ProdutoMapper mapper;
    private final ProdutoRepositorioAdapter repositorio;
    private final ProdutoValidator validador;
    private final ObterLoteDeProdutosPorIds obterLoteDeProdutosPorIds;

    public AtualizarProdutosEmLoteCasoDeUso(ProdutoMapper mapper,
                                            ProdutoRepositorioAdapter repositorio,
                                            ProdutoValidator validator,
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
