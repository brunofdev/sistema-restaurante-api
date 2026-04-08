package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapper;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.ProdutoRepositorioAdapter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ObterProdutosBaixaQntdCasoDeUso {

    private final ProdutoMapper mapeador;
    private final ProdutoRepositorioAdapter repositorio;

    public ObterProdutosBaixaQntdCasoDeUso(ProdutoMapper mapeador,
                                           ProdutoRepositorioAdapter repositorio) {
        this.mapeador = mapeador;
        this.repositorio = repositorio;
    }

    public List<ProdutoDTO> executar(){
        return mapeador.mapearListaDeEntidadeParaDTO(repositorio.findByQuantidadeAtualLessThan(11));
    }
}
