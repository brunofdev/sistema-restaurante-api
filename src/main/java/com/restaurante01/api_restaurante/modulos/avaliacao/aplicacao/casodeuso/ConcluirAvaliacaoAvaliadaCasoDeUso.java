package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada.ResponderAvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador.AvaliacaoMapeador;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.RespostaAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Map;
import java.util.Objects;


@Service
@AllArgsConstructor
public class ConcluirAvaliacaoAvaliadaCasoDeUso {
    private final AvaliacaoRepositorio repositorio;
    private final AvaliacaoMapeador mapeador;


    public void executar(Long idCliente, ResponderAvaliacaoDTO dto){
        Avaliacao avaliacao = encontrarAvaliacao(dto.idAvaliacao());
        verificaSeClienteDonoDaAvaliacao(idCliente, avaliacao);
        verificaSeAvaliacaoDisponivel(avaliacao);
        RespostaAvaliacao avaliacaoGeral = dto.avaliacaoDTO() != null ? mapeador.mapearRespostaAvaliacao(dto.avaliacaoDTO().nota(), dto.avaliacaoDTO().comentario()) : new RespostaAvaliacao(null, null);
        Map<Long, RespostaAvaliacao> itensAvaliados = dto.itensAvaliados() != null ? mapeador.mapearListaItensAvaliados(dto.itensAvaliados()) : Collections.emptyMap();
        avaliacao.concluirAvaliacao(avaliacaoGeral, itensAvaliados);
        repositorio.salvar(avaliacao);
    }
    private void verificaSeAvaliacaoDisponivel(Avaliacao avaliacao){
        if(avaliacao.getStatus() != StatusAvaliacao.DISPONIVEL){
            throw new AvaliacaoInvalidaExcecao("Só é possível concluir uma avaliação com status DISPONIVEL.");
        }
    }
    private void verificaSeClienteDonoDaAvaliacao(Long idCliente, Avaliacao avaliacao){
        if(!Objects.equals(avaliacao.getClienteId(), idCliente)){
            throw new AvaliacaoInvalidaExcecao("Cliente não é o dono desta avaliacao");
        }
    }

    private Avaliacao encontrarAvaliacao(Long id){
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new AvaliacaoInvalidaExcecao("Avaliação com id " + id + " não foi localizada"));
    }
}

