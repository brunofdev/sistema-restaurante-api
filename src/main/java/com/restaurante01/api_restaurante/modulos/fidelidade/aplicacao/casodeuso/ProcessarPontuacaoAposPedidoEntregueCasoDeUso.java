package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.FidelidadeNaoEncontradaExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.repositorio.FidelidadeRepositorio;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.servico.CalculadoraDeFidelidade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ProcessarPontuacaoAposPedidoEntregueCasoDeUso {

    private final FidelidadeRepositorio repositorio;

    @Transactional
    public void executar(Long clienteId, BigDecimal valorTotal) {
        Fidelidade fidelidade = repositorio.buscarPorClienteId(clienteId)
                .orElseThrow(() -> new FidelidadeNaoEncontradaExcecao(
                        "Fidelidade não encontrada para o cliente: " + clienteId));
        int pontos = CalculadoraDeFidelidade.calcular(valorTotal);
        fidelidade.creditarPontos(pontos, "Pontuação por pedido entregue");
        repositorio.salvar(fidelidade);
    }
}
