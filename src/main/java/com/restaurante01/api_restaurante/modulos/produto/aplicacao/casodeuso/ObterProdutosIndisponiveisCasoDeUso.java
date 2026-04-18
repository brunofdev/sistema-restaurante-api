package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapper;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoRepositorioAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObterProdutosIndisponiveisCasoDeUso {
    private final ProdutoMapper mapper;
    private final ProdutoRepositorioAdapter repositorio;

    public ObterProdutosIndisponiveisCasoDeUso(ProdutoMapper mapper, ProdutoRepositorioAdapter repositorio) {
        this.mapper = mapper;
        this.repositorio = repositorio;
    }

    public List<ProdutoDTO> executar() {
        return mapper.mapearListaDeEntidadeParaDTO(repositorio.findByDisponibilidade(false));
    }
}
