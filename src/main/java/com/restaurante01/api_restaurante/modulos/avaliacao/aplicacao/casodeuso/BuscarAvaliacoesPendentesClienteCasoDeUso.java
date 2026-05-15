package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoPendenteClienteDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador.AvaliacaoMapeador;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BuscarAvaliacoesPendentesClienteCasoDeUso {

    private final AvaliacaoMapeador mapeador;
    private final AvaliacaoRepositorio repositorio;

    public List<AvaliacaoPendenteClienteDTO> executar(Long idCliente){
        List<Avaliacao> avaliacoesEncontradasDoCliente = repositorio.buscarAvaliacoesPorClienteId(idCliente);
        return mapeador.mapearAvaliacoesPendentesDoCliente(avaliacoesEncontradasDoCliente);
    }

}
