package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.servico.CalculadoraDeFidelidade;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.FidelidadeNaoEncontradaExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.repositorio.FidelidadeRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ProcessaPontuacaoPedidoEntregueCasoDeUso {

    private final FidelidadeRepositorio repositorio;


    public void executar(Long idCliente, BigDecimal totalPedido) {
        Fidelidade fidelidade = repositorio.buscarPorClienteId(idCliente).orElseThrow(() -> new FidelidadeNaoEncontradaExcecao("Fidelidade não encontrada para o cliente com id " + idCliente));
        fidelidade.creditarPontos(CalculadoraDeFidelidade.calcular(totalPedido), "Pontução por pedido realizado");
        repositorio.salvar(fidelidade);
        System.out.println("Pontuação atualizada, cliente com id: " + idCliente + " | Recebeu em Pontos: " + CalculadoraDeFidelidade.calcular(totalPedido)); //colocado pra teste e ta chegando a pontuacao aqui corretamente
    }
}
