package com.restaurante01.api_restaurante.modulos.avaliacao.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.TentativaNotificacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import com.restaurante01.api_restaurante.modulos.avaliacao.infraestrutura.persistencia.AvaliacaoJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AvaliacaoJpaRepositorio implements AvaliacaoRepositorio {
    private final AvaliacaoJPA jpa;

    @Override
    public Avaliacao salvar(Avaliacao avaliacao){
        return jpa.save(avaliacao);
    }

    @Override
    public List<Avaliacao> salvarLista(List<Avaliacao> avaliacoes){
        return jpa.saveAll(avaliacoes);
    };
    @Override
    public Avaliacao atualizar(Avaliacao avaliacao){
        return jpa.save(avaliacao);
    }

    @Override
    public Optional<Avaliacao> buscarPorId(Long id){
        return jpa.findById(id);
    }

    @Override
    public Optional<Avaliacao> buscarPorPedidoId(Long pedidoId){
        return jpa.findByPedidoId(pedidoId);
    }

    @Override
    public List<Avaliacao> buscarTodos(){
        return jpa.findAll();
    }

    @Override
    public List<Avaliacao> buscarExpiradas(StatusAvaliacao status, LocalDateTime horarioAgora){
        return jpa.findByStatusAndDataExpiracaoBefore(status, horarioAgora);
    }

    @Override
    public List<Avaliacao> buscarTodasCriadasAte(StatusAvaliacao status, LocalDateTime horario){
        return jpa.findByStatusAndDataCriacaoBefore(status, horario);
    }

    @Override
    public List<Avaliacao> buscarPendentesDeRenotificacao(StatusAvaliacao status, TentativaNotificacao TentativaNotificacao, LocalDateTime horario){
        return jpa.findByStatusAndNumeroNotificacaoClienteAndDataCriacaoBefore(status, TentativaNotificacao, horario);
    }
}
