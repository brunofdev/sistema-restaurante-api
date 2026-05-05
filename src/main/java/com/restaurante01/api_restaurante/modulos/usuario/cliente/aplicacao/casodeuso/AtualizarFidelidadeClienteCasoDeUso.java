package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.excecao.ClienteNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.servico.CalculadoraDeFidelidade;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AtualizarFidelidadeClienteCasoDeUso {

    private final ClienteRepositorio repositorio;


    public AtualizarFidelidadeClienteCasoDeUso(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

   @Transactional
    public void executar(Long idCliente, BigDecimal totalPedido) {
        Cliente cliente = encontrarCliente(idCliente);
        int pontuacaoGanha = CalculadoraDeFidelidade.calcular(totalPedido);
        cliente.acrescentarPontuacao(pontuacaoGanha);
        repositorio.salvar(cliente);
    }


    private Cliente encontrarCliente(Long id){
        return repositorio.buscarPorId(id).orElseThrow(() -> new ClienteNaoEncontradoExcecao("Não encontramos nenhum Cliente com o id: " + id));
    }
}