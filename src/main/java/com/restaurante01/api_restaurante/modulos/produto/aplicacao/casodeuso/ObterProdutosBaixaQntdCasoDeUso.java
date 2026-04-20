package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoJpaAdaptador;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ObterProdutosBaixaQntdCasoDeUso {

    private final ProdutoMapeador mapeador;
    private final ProdutoJpaAdaptador repositorio;

    public ObterProdutosBaixaQntdCasoDeUso(ProdutoMapeador mapeador,
                                           ProdutoJpaAdaptador repositorio) {
        this.mapeador = mapeador;
        this.repositorio = repositorio;
    }

    public List<ProdutoDTO> executar(){
        return mapeador.mapearListaDeEntidadeParaDTO(repositorio.findByQuantidadeAtualLessThan(11));
    }
}
