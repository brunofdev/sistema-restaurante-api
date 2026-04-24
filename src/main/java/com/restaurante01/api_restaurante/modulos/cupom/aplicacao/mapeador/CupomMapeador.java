package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.mapeador;

import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CriarCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.PeriodoCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.CupomAdminDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.PeriodoCupomSaidaDTO;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.PeriodoCupom;
import org.springframework.stereotype.Component;

@Component
public class CupomMapeador {
    public PeriodoCupom mapearPeriodo(PeriodoCupomDTO dto) {
        if (dto == null) return null;

        return new PeriodoCupom(
                dto.dataInicio(),
                dto.horaInicio(),
                dto.dataFim(),
                dto.horaFim()
        );
    }


    public Cupom mapearCupom(CriarCupomDTO dto) {
        if (dto == null) return null;

        return Cupom.criar(
                dto.codigoCupom(),
                mapearPeriodo(dto.periodo()),
                dto.estaAtivo(),
                dto.quantidade(),
                dto.tipoDesconto(),
                dto.valorParaDesconto(),
                dto.valorTotalMinPedido(),
                dto.valorTotalMaxPedido()
        );
    }


    public PeriodoCupomSaidaDTO mapearDto(PeriodoCupom periodo) {
        return new PeriodoCupomSaidaDTO(
                periodo.getDataInicio().toString(),
                periodo.getHoraInicio().toString(),
                periodo.getDataFim().toString(),
                periodo.getHoraFim().toString());
    }


    public CupomAdminDTO mapearDtoDetalhado(Cupom cupom) {
        if (cupom == null) return null;

        return new CupomAdminDTO(
                cupom.getId(),
                cupom.getCodigoCupom() != null ? cupom.getCodigoCupom().getValor() : null,
                cupom.getTipoDesconto(),
                cupom.getQuantidade(),
                cupom.getValorParaDesconto(),
                cupom.getValorTotalMinPedido(),
                cupom.getValorTotalMaxPedido(),
                cupom.isEstaAtivo(),
                mapearDto(cupom.getPeriodoCupom()),
                cupom.getDataCriacao(),
                cupom.getDataAtualizacao(),
                cupom.getCriadoPor(),
                cupom.getAtualizadoPor()
        );
    }

    public CodigoCupom mapearCodigoCupom(String codigo){
        return new CodigoCupom(codigo);
    }
}