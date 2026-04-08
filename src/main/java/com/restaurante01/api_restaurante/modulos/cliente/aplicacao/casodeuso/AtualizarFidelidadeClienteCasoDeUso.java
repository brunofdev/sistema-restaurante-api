package com.restaurante01.api_restaurante.modulos.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cliente.aplicacao.servico.CalculadoraDeFidelidade;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AtualizarFidelidadeClienteCasoDeUso {

    private final ClienteRepositorio repository;
    private final CalculadoraDeFidelidade calculadoraDeFidelidade;

    public AtualizarFidelidadeClienteCasoDeUso(ClienteRepositorio repository, CalculadoraDeFidelidade calculadoraDeFidelidade) {
        this.repository = repository;
        this.calculadoraDeFidelidade = calculadoraDeFidelidade;
    }

    @Transactional
    public void executar(Cliente cliente, BigDecimal totalPedido) {
        int pontuacaoGanha = calculadoraDeFidelidade.calcular(totalPedido);
        cliente.acrescentarPontuacao(pontuacaoGanha);
        repository.salvar(cliente);
    }
}