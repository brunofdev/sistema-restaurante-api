package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.servico.CalculadoraDeFidelidade;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AtualizarFidelidadeClienteCasoDeUso {

    private final ClienteRepositorio repository;
    private final BuscarClientePorIdCasoDeUso buscarClientePorIdCasoDeUso;
    private final CalculadoraDeFidelidade calculadoraDeFidelidade;

    public AtualizarFidelidadeClienteCasoDeUso(ClienteRepositorio repository, BuscarClientePorIdCasoDeUso buscarClientePorIdCasoDeUso, CalculadoraDeFidelidade calculadoraDeFidelidade) {
        this.repository = repository;
        this.buscarClientePorIdCasoDeUso = buscarClientePorIdCasoDeUso;
        this.calculadoraDeFidelidade = calculadoraDeFidelidade;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executar(Long idCliente, BigDecimal totalPedido) {
        Cliente cliente = buscarClientePorIdCasoDeUso.executar(idCliente);
        int pontuacaoGanha = calculadoraDeFidelidade.calcular(totalPedido);
        cliente.acrescentarPontuacao(pontuacaoGanha);
        repository.salvar(cliente);
    }
}