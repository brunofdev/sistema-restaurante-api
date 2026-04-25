package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CupomAtualizadoDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.PeriodoCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.CupomAdminDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.PeriodoCupomSaidaDTO;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.mapeador.CupomMapeador;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.builder.*;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CodigoCupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarCupomCasoDeUsoTest {

    @Mock
    CupomRepositorio repositorio;

    @Mock
    CupomMapeador mapeador;

    @InjectMocks
    AtualizarCupomCasoDeUso casoDeUso;



    @Test
    @DisplayName("Deve lançar exececao caso cupom a ser atualizado não seja encontrado")
    void deveLancarExcecaoQuandoCupomNaoEncontrado(){
        Long id = 1L;
        CupomAtualizadoDTO cupomAdminDTO = CupomAtualizadoDTOBuilder.umCupomAtualizado().build();
        when(repositorio.obterPorId(id)).thenThrow(new CupomInvalidoExcecao("Cupom não encontrado com id: " + id));

        assertThrows(CupomInvalidoExcecao.class, () -> casoDeUso.executar(id, cupomAdminDTO));
    }
    @Test
    @DisplayName("Deve atualizar os campos de um determinado cupom encontrado por id")
    void deveAtualizarUmCupomEncontradoPorId(){
        Long id = 23L;
        CodigoCupom codigoCupomAtualizado = new CodigoCupom("TESTE123");
        PeriodoCupomDTO periodoEntrandoParaAtualizar = PeriodoCupomDTOBuilder.umPeriodo().comInicio("01/01/2026", "12:00").comFim("01/01/2030", "14:00").build();
        PeriodoCupomSaidaDTO periodoSaidaAtualizado = PeriodoCupomSaidaDTOBuilder.umPeriodoSaida().comInicio("01/01/2026", "12:00").comFim("01/01/2030", "14:00").build();
        CupomAtualizadoDTO cupomAtualizadoDTO = CupomAtualizadoDTOBuilder.umCupomAtualizado().comCodigo("TESTE123").comPeriodo(periodoEntrandoParaAtualizar).build();
        Cupom cupomEncontrado = CupomBuilder.umCupom().comCodigo(cupomAtualizadoDTO.codigo()).build();
        CupomAdminDTO cupomRetornado = CupomAdminDTOBuilder.umCupomAdminDTO().comId(id).comCodigo(cupomAtualizadoDTO.codigo()).comPeriodo(periodoSaidaAtualizado).build();


        when(repositorio.obterPorId(id)).thenReturn(cupomEncontrado);
        when(repositorio.existeCodigoCupom(codigoCupomAtualizado)).thenReturn(false);
        when(mapeador.mapearDtoDetalhado(cupomEncontrado)).thenReturn(cupomRetornado);


        CupomAdminDTO cupomResultado = casoDeUso.executar(id, cupomAtualizadoDTO);

        verify(repositorio).obterPorId(id);
        verify(repositorio).existeCodigoCupom(codigoCupomAtualizado);

        verify(repositorio).salvar(cupomEncontrado);

        verify(mapeador).mapearDtoDetalhado(cupomEncontrado);


        assertEquals(cupomRetornado, cupomResultado);

    }
    @Test
    @DisplayName("Deve lançar exceção quando novo código já existe em outro cupom")
    void deveLancarExcecaoQuandoNovoCodigoJaExiste(){
        Long id = 1L;
        CupomAtualizadoDTO dto = CupomAtualizadoDTOBuilder.umCupomAtualizado().comCodigo("JAEXISTE").build();
        Cupom cupomEncontrado = CupomBuilder.umCupom().comCodigo("CODIGOANTIGO").build();

        when(repositorio.obterPorId(id)).thenReturn(cupomEncontrado);
        when(repositorio.existeCodigoCupom(new CodigoCupom("JAEXISTE"))).thenReturn(true);

        assertThrows(CodigoCupomInvalidoExcecao.class, () -> casoDeUso.executar(id, dto));

        verify(repositorio, never()).salvar(any());
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o código enviado é o mesmo do cupom atual")
    void naoDeveLancarExcecaoQuandoCodigoEnviadoEOMesmoDoAtual(){
        Long id = 1L;
        CupomAtualizadoDTO dto = CupomAtualizadoDTOBuilder.umCupomAtualizado().comCodigo("MESMOCOD").build();
        Cupom cupomEncontrado = CupomBuilder.umCupom().comCodigo("MESMOCOD").build();
        CupomAdminDTO cupomRetornado = CupomAdminDTOBuilder.umCupomAdminDTO().comCodigo("MESMOCOD").build();

        when(repositorio.obterPorId(id)).thenReturn(cupomEncontrado);
        when(repositorio.existeCodigoCupom(new CodigoCupom("MESMOCOD"))).thenReturn(true); // existe, mas é o mesmo!
        when(mapeador.mapearDtoDetalhado(cupomEncontrado)).thenReturn(cupomRetornado);

        assertDoesNotThrow(() -> casoDeUso.executar(id, dto));

        verify(repositorio).salvar(cupomEncontrado);
    }

}