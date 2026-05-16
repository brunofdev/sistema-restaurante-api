package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.ClassificacaoAvaliacao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.FidelidadeNaoEncontradaExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.repositorio.FidelidadeRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProcessaPontuacaoAposAvaliacaoConcluidaCasoDeUso {
    private final FidelidadeRepositorio repositorio;
    private final Integer PONTUACAO_POR_ITEM_AVALIADO = 2;
    private final Integer PONTUACAO_AVALIACAO_PEDIDO_GERAL_NAO_AVALIADO = 0;
    private final Integer PONTUACAO_AVALIACAO_PEDIDO_GERAL_INSATISFEITO = 1;
    private final Integer PONTUACAO_AVALIACAO_PEDIDO_GERAL_MODERADO = 2;
    private final Integer PONTUACAO_AVALIACAO_PEDIDO_GERAL_SATISFEITO = 3;

    public void executar(Long clienteId, Integer numeroDeItensAvaliados, ClassificacaoAvaliacao classificacaoAvaliacao) {
        Fidelidade fidelidade = repositorio.buscarPorClienteId(clienteId).orElseThrow(() -> new FidelidadeNaoEncontradaExcecao("Fidelidade não localizada"));
        fidelidade.creditarPontos(calcularPontuacaoDeAvaliacao(numeroDeItensAvaliados, classificacaoAvaliacao), "Pontuação gerada por ter avaliado pedidos e itens");
    }

    private Integer calcularPontuacaoDeAvaliacao(Integer numeroItensAvaliados, ClassificacaoAvaliacao classificacaoAvaliacao){
        Integer pontuacaoDeAvaliacao = 0;
        switch (classificacaoAvaliacao){
            case NAO_AVALIADO -> pontuacaoDeAvaliacao += PONTUACAO_AVALIACAO_PEDIDO_GERAL_NAO_AVALIADO;
            case INSATISFEITO -> pontuacaoDeAvaliacao += PONTUACAO_AVALIACAO_PEDIDO_GERAL_INSATISFEITO;
            case MODERADO ->  pontuacaoDeAvaliacao += PONTUACAO_AVALIACAO_PEDIDO_GERAL_MODERADO;
            case SATISFEITO -> pontuacaoDeAvaliacao += PONTUACAO_AVALIACAO_PEDIDO_GERAL_SATISFEITO;
        }

        return numeroItensAvaliados * PONTUACAO_POR_ITEM_AVALIADO + pontuacaoDeAvaliacao;
    }

}


//AVALIACAO DE ITEM  = 2 PONTOS POR ITEM AVALIADO
//AVALIACAO GERAL NOTA = 3 PONTOS
