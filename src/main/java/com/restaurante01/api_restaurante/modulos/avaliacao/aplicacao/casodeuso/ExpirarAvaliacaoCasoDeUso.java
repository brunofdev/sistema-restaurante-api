package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExpirarAvaliacaoCasoDeUso {

    private final AvaliacaoRepositorio repositorio;

    public void executar(Long id){
        Avaliacao avaliacao  = encontrarAvaliacao(id);
        avaliacao.mudarStatusAvaliacao(StatusAvaliacao.EXPIRADA);
        repositorio.salvar(avaliacao);
    }
     private Avaliacao encontrarAvaliacao(Long id){
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new AvaliacaoInvalidaExcecao("Avaliação com id " + id + " não foi localizada"));
    }
}
