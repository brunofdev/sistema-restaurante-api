package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;


import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoJpaAdaptador;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ObterLoteDeProdutosPorIds {

    private final ProdutoJpaAdaptador repositorio;

    public ObterLoteDeProdutosPorIds(ProdutoJpaAdaptador repositorio) {
        this.repositorio = repositorio;
    }

    public List<Produto> executar(Set<Long> idsMapeados){
        return repositorio.findAllById(idsMapeados);
    }
}
