package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CriarCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.CupomAdminDTO;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.mapeador.CupomMapeador;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import org.springframework.stereotype.Service;

@Service
public class CriarCupomCasoDeUso {

    private final CupomMapeador mapeador;
    private final CupomRepositorio repositorio;

    public CriarCupomCasoDeUso(CupomMapeador mapeador, CupomRepositorio repositorio) {
        this.mapeador = mapeador;
        this.repositorio = repositorio;
    }

    public CupomAdminDTO executar(CriarCupomDTO dto){
        verificaSeExisteCodigoCupom(dto.codigoCupom());
        Cupom novoCupom =  mapeador.mapearCupom(dto);
        repositorio.salvar(novoCupom);
        return mapeador.mapearDtoDetalhado(novoCupom);
    }

    private void verificaSeExisteCodigoCupom(String codigoCupom){
        CodigoCupom codigoCupom1 = mapeador.mapearCodigoCupom(codigoCupom);
        if (repositorio.existeCodigoCupom(codigoCupom1)){
            throw new CupomInvalidoExcecao("Ja existe cupom registrado com este Código");
        };
    }
}
