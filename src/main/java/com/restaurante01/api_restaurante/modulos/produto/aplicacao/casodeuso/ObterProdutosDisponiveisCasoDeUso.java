package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoJpaAdaptador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObterProdutosDisponiveisCasoDeUso {

    private final ProdutoMapeador mapper;
    private final ProdutoJpaAdaptador repositorio;

    public ObterProdutosDisponiveisCasoDeUso(ProdutoMapeador mapper, ProdutoJpaAdaptador repositorio) {
        this.mapper = mapper;
        this.repositorio = repositorio;
    }

    public List<ProdutoDTO> executar(){
        return mapper.mapearListaDeEntidadeParaDTO(repositorio.findByDisponibilidade(true));
    }
}
