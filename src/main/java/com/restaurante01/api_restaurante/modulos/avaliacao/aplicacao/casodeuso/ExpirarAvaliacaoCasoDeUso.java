package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;



@Service
@AllArgsConstructor
public class ExpirarAvaliacaoCasoDeUso {

    private final AvaliacaoRepositorio repositorio;

    @Transactional
    public void executar(){
        List<Avaliacao> avaliacoesExpiradas = repositorio.buscarTodasCriadasAte(StatusAvaliacao.DISPONIVEL, LocalDateTime.now().minusDays(7));
        if(avaliacoesExpiradas.isEmpty()){
            return;
        }
        avaliacoesExpiradas.forEach(Avaliacao::expirarAvaliacao);
        repositorio.salvarLista(avaliacoesExpiradas);
    }
}
