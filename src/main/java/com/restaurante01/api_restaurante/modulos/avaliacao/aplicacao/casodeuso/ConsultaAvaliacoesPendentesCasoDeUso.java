package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador.AvaliacaoMapeador;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsultaAvaliacoesPendentesCasoDeUso {
        private final AvaliacaoRepositorio repositorio;
        private final AvaliacaoMapeador mapeador;

        public List<AvaliacaoDTO> executar (){
            List<Avaliacao> avaliacoesPendentes = repositorio.buscarTodasPorStatus(StatusAvaliacao.PENDENTE);
            return mapeador.mapearListaAvaliacoesDTO(avaliacoesPendentes);
        }

}
