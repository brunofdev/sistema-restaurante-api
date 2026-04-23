package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.PeriodoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.PeriodoInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.ValorMaxPedidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.ValorMinPedidoExcecao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValidarCupomCasoDeUso {

    private final CupomRepositorio repositorio;

    public ValidarCupomCasoDeUso(CupomRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Cupom executar(String codigo, BigDecimal valorBrutoTotalPedido){
        Cupom cupom = repositorio.obterPorCodigo(codigo);
        validarPeriodo(cupom.getPeriodoCupom());
        validarQuantidade(cupom);
        validarSeEstaAtivo(cupom);
        validarSeEstaDentroValorMin(cupom, valorBrutoTotalPedido);
        validarSeEstaDentroValorMax(cupom, valorBrutoTotalPedido);
        return cupom;

    }
    private void validarPeriodo(PeriodoCupom periodoCupom){
        if(!periodoCupom.estaVigente()){
            throw  new PeriodoInvalidoExcecao("Cupom expirado");
        }
    }
    private void validarQuantidade(Cupom cupom){
        if (!cupom.possuiQtdDisponivelParaConsumo()){
            throw new CupomInvalidoExcecao("Cupom não possui mais quantidade para consumo");
        }
    }
    private void validarSeEstaAtivo(Cupom cupom){
        if(!cupom.isEstaAtivo()){
            throw new CupomInvalidoExcecao("Este Cupom está inativo no momento");
        }
    }
    private void validarSeEstaDentroValorMin(Cupom cupom, BigDecimal valorBrutoTotalPedido){
        if(cupom.getValorTotalMinPedido().compareTo(valorBrutoTotalPedido) > 0){
            throw new ValorMinPedidoExcecao("Valor minimo do cupom: " + cupom.getValorTotalMinPedido());
        }
    }
    private void validarSeEstaDentroValorMax(Cupom cupom, BigDecimal valorBrutoTotalPedido){
        if(cupom.getValorTotalMaxPedido().compareTo(valorBrutoTotalPedido) < 0){
            throw new ValorMaxPedidoExcecao("Valor maximo do cupom: " + cupom.getValorTotalMaxPedido());
        }
    }
}
