package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CupomBuilder;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.PeriodoInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.ValorMaxPedidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.ValorMinPedidoExcecao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidarCupomCasoDeUsoTest {

    @Mock private CupomRepositorio repositorio;

    @InjectMocks
    public ValidarCupomCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve lançar CupomInvalidoExcecao quando  não existir, a excecao é lançada no CupomRepositorio.obterPorCodigo(String)")
    void deveLancarExcecaoQuandoCupomNaoExiste(){
        String codigoCupom = "TESTE123";
        BigDecimal valorTotalBrutoPedido = new BigDecimal(200);

        when(repositorio.obterPorCodigo(codigoCupom)).thenThrow(new CupomInvalidoExcecao("Cupom Informado: >> \" + codigo +  \" << é Inválido\""));

        assertThrows(CupomInvalidoExcecao.class, () -> casoDeUso.executar(codigoCupom, valorTotalBrutoPedido));
    }

    @Test
    @DisplayName("Deve lançar PeriodoInvalidoExcecao quando o dia atual estiver em desacordo com o Periodo do cupom")
    void deveLancarExcecaoQuandoDiaAtualEmDesacordoComPeriodo(){
        String codigoCupom = "CUPOM10";
        BigDecimal valorTotalBrutoPedido = new BigDecimal(200);
        Cupom cupomLocalizado  = CupomBuilder.umCupom().expirado().build();

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomLocalizado);

        assertThrows(PeriodoInvalidoExcecao.class, () -> casoDeUso.executar(codigoCupom, valorTotalBrutoPedido));
    }

    @Test
    @DisplayName("Deve lançar CupomInvalidoExcecao quando quantidade do cupom estar zerada")
    void deveLancarExcecaoQuandoQuantidadeSerZero(){
        String codigoCupom = "CUPOM10";
        BigDecimal valorTotalBrutoPedido = new BigDecimal(200);
        Cupom cupomLocalizado = CupomBuilder.umCupom().comQuantidade(0).build();

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomLocalizado);

        assertThrows(CupomInvalidoExcecao.class, () -> casoDeUso.executar(codigoCupom, valorTotalBrutoPedido));
    }

    @Test
    @DisplayName("Deve lancar CupomInvalidoExcecao se não estiver ativo = true")
    void deveLancarExcecaoCupomNaoAtivo(){
        String codigoCupom = "CUPOM10";
        BigDecimal valorTotalBrutoPedido = new BigDecimal(200);
        Cupom cupomLocalizado = CupomBuilder.umCupom().inativo().build();

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomLocalizado);

        assertThrows(CupomInvalidoExcecao.class, () -> casoDeUso.executar(codigoCupom, valorTotalBrutoPedido));

    }

    @Test
    @DisplayName("Deve lançar ValorMinPedidoExcecao se valorPedido for menor que valorTotalMinPedido")
    void deveLancarExcecaoValorPedidoMenorValorMinCupom(){
        String codigoCupom = "CUPOM10";
        BigDecimal valorTotalBrutoPedido = new BigDecimal(50);
        Cupom cupomLocalizado = CupomBuilder.umCupom().comValorMinPedido(new BigDecimal(100)).build();

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomLocalizado);

        assertThrows(ValorMinPedidoExcecao.class, () -> casoDeUso.executar(codigoCupom, valorTotalBrutoPedido));
    }

    @Test
    @DisplayName("Deve lançar excecao quando o valorBrutoTotalPedido ultrapassar valorMaxPedido do cupom")
    void deveLancarExcecaoValorPedidoMaiorValorMaxCupom(){
        String codigoCupom = "CUPOM10";
        BigDecimal valorTotalBrutoPedido = new BigDecimal(150);
        Cupom cupomLocalizado = CupomBuilder.umCupom().comValorMaxPedido(new BigDecimal(100)).build();

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomLocalizado);

        assertThrows(ValorMaxPedidoExcecao.class, () -> casoDeUso.executar(codigoCupom, valorTotalBrutoPedido));
    }

    @Test
    @DisplayName("Deve retornar um cupom validado e disponivel para ser consumado")
    void deveRetornarCupomValido(){
        String codigoCupom = "CUPOM10";
        BigDecimal valorTotalBrutoPedido = new BigDecimal(150);
        Cupom cupomLocalizado = CupomBuilder.umCupom().build();

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomLocalizado);

        Cupom cupomValido = casoDeUso.executar(codigoCupom, valorTotalBrutoPedido);

        assertEquals(cupomValido, cupomLocalizado);

        assertNotNull(cupomValido);
        assertEquals(codigoCupom, cupomValido.getCodigoCupom().getValor());
        assertTrue(cupomValido.isEstaAtivo());
        verify(repositorio, times(1)).obterPorCodigo(codigoCupom);
    }
}