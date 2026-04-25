package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CupomAtualizadoDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.CupomAdminDTO;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.mapeador.CupomMapeador;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.PeriodoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CodigoCupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import org.springframework.stereotype.Service;

@Service
public class AtualizarCupomCasoDeUso {


    private final CupomRepositorio repositorio;
    private final CupomMapeador mapeador;

    public AtualizarCupomCasoDeUso(CupomRepositorio repositorio, CupomMapeador mapeador) {
        this.repositorio = repositorio;
        this.mapeador = mapeador;
    }

    public CupomAdminDTO executar(Long id, CupomAtualizadoDTO dto){
        Cupom cupom = repositorio.obterPorId(id);
        validaSeCodigoNaoExiste(dto.codigo(), cupom.getCodigoCupom().getValor());
        atualizaOsCampos(cupom, dto);
        repositorio.salvar(cupom);
        return mapeador.mapearDtoDetalhado(cupom);
    }
    private void validaSeCodigoNaoExiste(String novoCodigoCupom, String codigoOriginal){
        if(repositorio.existeCodigoCupom(new CodigoCupom(novoCodigoCupom)) && !novoCodigoCupom.equals(codigoOriginal)){
            throw new CodigoCupomInvalidoExcecao("Ja existe cupom com este Codigo cadastrado no sistema");
            }
        }
    private void atualizaOsCampos(Cupom cupom, CupomAtualizadoDTO dto){
        PeriodoCupom novoPeriodo = mapeador.mapearPeriodo(dto.periodo());
        cupom.atualizar(
                dto.codigo(),
                novoPeriodo,
                dto.quantidadeRestante(),
                dto.valorDesconto(),
                dto.estaAtivo(),
                dto.valorTotalMinPedido(),
                dto.valorTotalMaxPedido(),
                dto.tipoDesconto());
    }
}
