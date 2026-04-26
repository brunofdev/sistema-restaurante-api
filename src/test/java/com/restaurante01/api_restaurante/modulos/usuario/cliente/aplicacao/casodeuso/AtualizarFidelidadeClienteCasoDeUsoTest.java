package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.ClienteBuilder;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.servico.CalculadoraDeFidelidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AtualizarFidelidadeClienteCasoDeUsoTest {

    @Mock
    ClienteRepositorio repositorio;

    @InjectMocks AtualizarFidelidadeClienteCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve encontrar cliente e acrecentar a pontuação corretamente")
    void deveBuscarClienteEAcrescentarPontuacaoCalculada(){
        BigDecimal valorTotalPedido = new BigDecimal(50);
        Cliente clienteEncontrado = ClienteBuilder.umCliente().comPontuacao(5).build();
        int pontuacaoGanha = CalculadoraDeFidelidade.calcular(valorTotalPedido);
        int pontuacaoAcrescentada = clienteEncontrado.getPontuacaoFidelidade().getValor() + pontuacaoGanha;

        when(repositorio.buscarPorId(clienteEncontrado.getId())).thenReturn(Optional.of(clienteEncontrado));
        casoDeUso.executar(clienteEncontrado.getId(), valorTotalPedido);

        assertEquals(pontuacaoAcrescentada, clienteEncontrado.getPontuacaoFidelidade().getValor());
    }

    @Test
    @DisplayName("Deve Salvar o cliente no banco com a pontuação atualizada")
    void deveSalvarClienteComNovaPontuacao(){
        BigDecimal valorTotalPedido = new BigDecimal(50);
        Cliente clienteEncontrado = ClienteBuilder.umCliente().comPontuacao(10).build();
        int pontuacaoGanha = CalculadoraDeFidelidade.calcular(valorTotalPedido);
        int pontuacaoAcrescentada = clienteEncontrado.getPontuacaoFidelidade().getValor() + pontuacaoGanha;

        when(repositorio.buscarPorId(clienteEncontrado.getId())).thenReturn(Optional.of(clienteEncontrado));

        casoDeUso.executar(clienteEncontrado.getId(), valorTotalPedido);

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(repositorio).salvar(captor.capture());

        Cliente clienteSalvo = captor.getValue();
        assertEquals(pontuacaoAcrescentada, clienteSalvo.getPontuacaoFidelidade().getValor());
    }

}