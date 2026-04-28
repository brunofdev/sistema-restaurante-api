package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.*;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.builder.CupomBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.CupomUtilizado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidarCupomCasoDeUsoTest {

    @Mock private CupomRepositorio repositorio;

    @InjectMocks
    public ValidarCupomCasoDeUso casoDeUso;

    private String cupom;
    private CodigoCupom codigoCupom;
    private BigDecimal valorTotalBrutoPedido;

    @BeforeEach
    void configurarCenario(){
        cupom = "TESTE123";
        codigoCupom = new CodigoCupom("TESTE123");
        valorTotalBrutoPedido = new BigDecimal(200);
    }

    @Test
    @DisplayName("Deve lançar CupomInvalidoExcecao quando  não existir, a excecao é lançada no CupomRepositorio.obterPorCodigo(String)")
    void deveLancarExcecaoQuandoCupomNaoExiste(){
        CupomUtilizado cupomUtilizado = new CupomUtilizado(cupom, valorTotalBrutoPedido, Optional.empty());

        when(repositorio.obterPorCodigo(codigoCupom))
                .thenReturn(Optional.empty());

        assertThrows(CupomInvalidoExcecao.class, () -> casoDeUso.executar(cupomUtilizado));
    }

    @Test
    @DisplayName("Deve lançar PeriodoInvalidoExcecao quando o dia atual estiver em desacordo com o Periodo do cupom")
    void deveLancarExcecaoQuandoDiaAtualEmDesacordoComPeriodo(){
        Cupom cupomLocalizado  = CupomBuilder.umCupom().expirado().build();
        CupomUtilizado cupomUtilizado = new CupomUtilizado(cupom, valorTotalBrutoPedido, Optional.empty());

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(Optional.of(cupomLocalizado));

        assertThrows(PeriodoInvalidoExcecao.class, () -> casoDeUso.executar(cupomUtilizado));
    }

    @Test
    @DisplayName("Deve lançar CupomInvalidoExcecao quando quantidade do cupom estar zerada")
    void deveLancarExcecaoQuandoQuantidadeSerZero(){
        Cupom cupomLocalizado = CupomBuilder.umCupom().comQuantidade(0).build();
        CupomUtilizado cupomUtilizado = new CupomUtilizado(cupom, valorTotalBrutoPedido, Optional.empty());

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(Optional.of(cupomLocalizado));

        assertThrows(CupomInvalidoExcecao.class, () -> casoDeUso.executar(cupomUtilizado));
    }

    @Test
    @DisplayName("Deve lancar CupomInvalidoExcecao se não estiver ativo = true")
    void deveLancarExcecaoCupomNaoAtivo(){
        Cupom cupomLocalizado = CupomBuilder.umCupom().inativo().build();
        CupomUtilizado cupomUtilizado = new CupomUtilizado(cupom, valorTotalBrutoPedido, Optional.empty());

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(Optional.of(cupomLocalizado));

        assertThrows(CupomInvalidoExcecao.class, () -> casoDeUso.executar(cupomUtilizado));
    }

    @Test
    @DisplayName("Deve lançar ValorMinPedidoExcecao se valorPedido for menor que valorTotalMinPedido")
    void deveLancarExcecaoValorPedidoMenorValorMinCupom(){
        BigDecimal valorTotalBrutoPedido = new BigDecimal(50);
        Cupom cupomLocalizado = CupomBuilder.umCupom().comValorMinPedido(new BigDecimal(100)).build();
        CupomUtilizado cupomUtilizado = new CupomUtilizado(cupom, valorTotalBrutoPedido, Optional.empty());

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(Optional.of(cupomLocalizado));

        assertThrows(ValorMinPedidoExcecao.class, () -> casoDeUso.executar(cupomUtilizado));
    }

    @Test
    @DisplayName("Deve lançar excecao quando o valorBrutoTotalPedido ultrapassar valorMaxPedido do cupom")
    void deveLancarExcecaoValorPedidoMaiorValorMaxCupom(){
        BigDecimal valorTotalBrutoPedido = new BigDecimal(150);
        Cupom cupomLocalizado = CupomBuilder.umCupom().comValorMaxPedido(new BigDecimal(100)).build();
        CupomUtilizado cupomUtilizado = new CupomUtilizado(cupom, valorTotalBrutoPedido, Optional.empty());

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(Optional.of(cupomLocalizado));

        assertThrows(ValorMaxPedidoExcecao.class, () -> casoDeUso.executar(cupomUtilizado));
    }

    @Test
    @DisplayName("Deve retornar um cupom validado e disponivel para ser consumado")
    void deveRetornarCupomValido(){
        Cupom cupomLocalizado = CupomBuilder.umCupom().comCodigo(cupom).build();
        Optional<LocalDateTime> dataUltimaUtilizacaoDoCupom = Optional.of(LocalDateTime.now().minusDays(25));
        CupomUtilizado cupomUtilizado = new CupomUtilizado(cupom, valorTotalBrutoPedido, dataUltimaUtilizacaoDoCupom);

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(Optional.of(cupomLocalizado));

        Cupom cupomValido = casoDeUso.executar(cupomUtilizado);

        assertEquals(cupomValido, cupomLocalizado);

        assertNotNull(cupomValido);
        assertEquals(codigoCupom, cupomValido.getCodigoCupom());
        assertTrue(cupomValido.isEstaAtivo());
        verify(repositorio, times(1)).obterPorCodigo(codigoCupom);
    }
    @Test
    @DisplayName("Deve  esperar uma excecao do enum caso seja enviado uma data de utilizacao e a mesma esteja dendro do periodo de recorrencia")
    void deveLancarExcecaoSeDentroDePeriodoRecorrente(){
        Cupom cupomLocalizado = CupomBuilder.umCupom().comCodigo(cupom).recorrenciaDe15Dias().build();
        Optional<LocalDateTime> dataUltimaUtilizacaoDoCupom = Optional.of(LocalDateTime.now().minusDays(14));

        CupomUtilizado cupomUtilizado = new CupomUtilizado(cupom, valorTotalBrutoPedido, dataUltimaUtilizacaoDoCupom);

        when(repositorio.obterPorCodigo(codigoCupom)).thenReturn(Optional.of(cupomLocalizado));

        assertThrows(CupomNaoPodeSerConsumidoExcecao.class, () -> casoDeUso.executar(cupomUtilizado));
    }
}