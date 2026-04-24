package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;


import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CupomBuilder;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomNaoPodeSerConsumidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ConsumirCupomCasoDeUsoTest {

    @Mock CupomRepositorio repositorio;

    @InjectMocks ConsumirCupomCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve lançar excecao quando o cupom não for encontrado")
    void deveLancarExcecaoQuandoCupomNaoEncontrado(){
        CodigoCupom codigoCupom = new CodigoCupom("CUPOM10");

        when(repositorio.obterPorCodigo(codigoCupom)).thenThrow(new CupomInvalidoExcecao("Cupom Informado: >> \" + codigo +  \" << é Inválido\""));

        assertThrows(CupomInvalidoExcecao.class, () -> casoDeUso.executar(codigoCupom));

    }
    @Test
    @DisplayName("Deve lançar excecao quando Cupom esta com quantidade zero")
    void deveLancarExcecaoQuandoCupomNaoPossuiQuantidade(){
        CodigoCupom codigoCupom = new CodigoCupom("CUPOM10");
        Cupom cupomEncontrado = CupomBuilder.umCupom().comCodigo("CUPOM10").comQuantidade(0).build();

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomEncontrado);

        assertThrows(CupomNaoPodeSerConsumidoExcecao.class, () -> casoDeUso.executar(codigoCupom));
    }

    @Test
    @DisplayName("Deve subtrair da quantidade do cupom encontrado")
    void deveSubtrairQuantidadeDoCupomEncontrado(){
        CodigoCupom codigoCupom = new CodigoCupom("CUPOM10");
        Cupom cupomEncontrado = CupomBuilder.umCupom().comCodigo("CUPOM10").comQuantidade(10).build();

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomEncontrado);
        Cupom cupomConsumido = casoDeUso.executar(codigoCupom);

        assertEquals(cupomEncontrado.getCodigoCupom(), cupomConsumido.getCodigoCupom());
        assertEquals(9, cupomConsumido.getQuantidade());
    }

    @Test
    @DisplayName("Deve subtrair e verificar se cupom foi salvo")
    void deveSalvarCupomConsumido(){
        CodigoCupom codigoCupom = new CodigoCupom("CUPOM10");
        Cupom cupomEncontrado = CupomBuilder.umCupom().comCodigo("CUPOM10").comQuantidade(10).build();

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomEncontrado);
        casoDeUso.executar(codigoCupom);

        ArgumentCaptor<Cupom> captor = ArgumentCaptor.forClass(Cupom.class);
        verify(repositorio).salvar(captor.capture());

        Cupom cupomSalvo = captor.getValue();
        assertEquals(9, cupomSalvo.getQuantidade());
        assertEquals(cupomSalvo.getCodigoCupom(), cupomEncontrado.getCodigoCupom());
    }
    @Test
    @DisplayName("Deve verificar se o produto foi desativado após consumo da ultima unidade")
    void deveDesativarCupomAposConsumoUltimaUnidade(){
        CodigoCupom codigoCupom = new CodigoCupom("CUPOM10");
        Cupom cupomEncontrado = CupomBuilder.umCupom().ativo().comCodigo("CUPOM10").comQuantidade(1).build();

        assertTrue(cupomEncontrado.isEstaAtivo());

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(cupomEncontrado);
        casoDeUso.executar(codigoCupom);

        ArgumentCaptor<Cupom> captor = ArgumentCaptor.forClass(Cupom.class);
        verify(repositorio).salvar(captor.capture());

        Cupom cupomSalvo = captor.getValue();

        assertEquals(0, cupomSalvo.getQuantidade());
        assertFalse(cupomSalvo.isEstaAtivo());
        assertEquals(cupomSalvo.getCodigoCupom(), cupomEncontrado.getCodigoCupom());

    }
}