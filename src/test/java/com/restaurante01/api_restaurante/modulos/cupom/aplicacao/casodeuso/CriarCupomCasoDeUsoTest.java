package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.builders.CriarCupomDTOBuilder;
import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CriarCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.CupomAdminDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.PeriodoCupomSaidaDTO;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.mapeador.CupomMapeador;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CupomBuilder;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CriarCupomCasoDeUsoTest {

    @Mock
    CupomMapeador mapeador;
    @Mock
    CupomRepositorio repositorio;
    @InjectMocks CriarCupomCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve lançar excecao quando codigo do cupom ja esta cadastrado")
    void deveLancarExcecaoSeCodigoCupomExiste(){
        CriarCupomDTO criarCupomDTO = CriarCupomDTOBuilder.umCupom().comCodigo("TESTE123").build();
        CodigoCupom codigoCupom = new CodigoCupom("TESTE123");

        when(mapeador.mapearCodigoCupom(criarCupomDTO.codigoCupom())).thenReturn(codigoCupom);
        when(repositorio.existeCodigoCupom(codigoCupom)).thenReturn(true);

        assertThrows(CupomInvalidoExcecao.class, () -> casoDeUso.executar(criarCupomDTO));

    }
    @Test
    @DisplayName("Deve lançar qualquer tipo de excecao que estoura dentro do mapeamento, pois a entidade sabe se auto validar")
    void deveRepassarQualquerExcecaoLancadaNoMapeamento(){
        CriarCupomDTO criarCupomDTO = CriarCupomDTOBuilder.umCupom().comCodigo("Invalido123").build();

        when(mapeador.mapearCodigoCupom(criarCupomDTO.codigoCupom())).thenThrow(new CupomInvalidoExcecao("Teste"));

        assertThrows(RegraDeNegocioExcecao.class, () -> casoDeUso.executar(criarCupomDTO));

    }
    @Test
    @DisplayName("Deve criar um cupom novo corretamente")
    void deveCriarCorretamenteUmCupom(){
       CriarCupomDTO dtoValido = CriarCupomDTOBuilder.umCupom().build();
       CodigoCupom codigoCupom = new CodigoCupom(dtoValido.codigoCupom());
       Cupom cupom = CupomBuilder.umCupom().build();
       PeriodoCupomSaidaDTO periodoCupomSaidaDTO = new PeriodoCupomSaidaDTO(dtoValido.periodo().dataInicio(), cupom.getPeriodoCupom().getHoraInicio().toString(), cupom.getPeriodoCupom().getDataFim().toString(), cupom.getPeriodoCupom().getHoraFim().toString());
       CupomAdminDTO cupomEsperado = new CupomAdminDTO(
               cupom.getId(),
               cupom.getCodigoCupom().getValor(),
               cupom.getTipoDesconto(),
               cupom.getQuantidade(),
               cupom.getValorParaDesconto(),
               cupom.getValorTotalMinPedido(),
               cupom.getValorTotalMaxPedido(),
               cupom.isEstaAtivo(),
               periodoCupomSaidaDTO,
               cupom.getDataCriacao(),
               cupom.getDataAtualizacao(),
               cupom.getCriadoPor(),
               cupom.getAtualizadoPor());

       when(mapeador.mapearCodigoCupom(dtoValido.codigoCupom())).thenReturn(codigoCupom);
       when(repositorio.existeCodigoCupom(codigoCupom)).thenReturn(false);
       when(mapeador.mapearCupom(dtoValido)).thenReturn(cupom);
       when(mapeador.mapearDtoDetalhado(cupom)).thenReturn(cupomEsperado);


       CupomAdminDTO resultado = casoDeUso.executar(dtoValido);

       assertEquals(cupomEsperado, resultado);
       verify(mapeador).mapearCupom(dtoValido);
       verify(mapeador).mapearDtoDetalhado(cupom);

    }

    @Test
    @DisplayName("Deve salvar o cupom no repositório após criação")
    void deveSalvarCupomNoRepositorio(){
        CriarCupomDTO dtoValido = CriarCupomDTOBuilder.umCupom().build();
        CodigoCupom codigoCupom = new CodigoCupom(dtoValido.codigoCupom());
        Cupom cupom = CupomBuilder.umCupom().build();

        when(mapeador.mapearCodigoCupom(dtoValido.codigoCupom())).thenReturn(codigoCupom);
        when(repositorio.existeCodigoCupom(codigoCupom)).thenReturn(false);
        when(mapeador.mapearCupom(dtoValido)).thenReturn(cupom);

        casoDeUso.executar(dtoValido);

        verify(repositorio).salvar(cupom);
    }

}