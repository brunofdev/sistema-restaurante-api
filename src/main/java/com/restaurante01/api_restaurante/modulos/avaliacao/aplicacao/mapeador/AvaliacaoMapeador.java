package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador;

import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada.ItemAvaliadoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoItemDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoPendenteClienteDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.ItensDoPedidoSaidaDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.AvaliacaoItem;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.RespostaAvaliacao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoAvaliacaoPayload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AvaliacaoMapeador {

    public AvaliacaoItem mapearItemPedido(ItemPedidoAvaliacaoPayload itemPedido) {
        return AvaliacaoItem.criar(
                itemPedido.idProduto(),
                itemPedido.nome(),
                new RespostaAvaliacao(null, null)
        );
    }

    public List<AvaliacaoItem> mapearItensPedido(List<ItemPedidoAvaliacaoPayload> itensPedido) {
        return itensPedido.stream()
                .collect(Collectors.groupingBy(ItemPedidoAvaliacaoPayload::idProduto))
                .values().stream()
                .map(itemPedidos -> mapearItemPedido(itemPedidos.get(0)))
                .toList();
        }

    public ItensDoPedidoSaidaDTO mapearItensPedidoSaidaDTO(AvaliacaoItem item){
        return new ItensDoPedidoSaidaDTO(
                item.getId(),
                item.getProdutoId(),
                item.getNomeProdutoAvaliacao()
        );
    }
    public List<ItensDoPedidoSaidaDTO> mapearListaItensPedidoSaidaDTO(List<AvaliacaoItem> itensPedido){
        return itensPedido.stream().map(this::mapearItensPedidoSaidaDTO).collect(Collectors.toList());
    }

    public AvaliacaoPendenteClienteDTO mapearAvaliacaoPendenteCliente(Avaliacao avaliacao){
        return new AvaliacaoPendenteClienteDTO(
                avaliacao.getId(),
                avaliacao.getDataCriacao(),
                mapearListaItensPedidoSaidaDTO(avaliacao.getItensAvaliados())
        );
    }

    public List<AvaliacaoPendenteClienteDTO> mapearAvaliacoesPendentesDoCliente(List<Avaliacao> avaliacoes){
        return avaliacoes.stream().map(this::mapearAvaliacaoPendenteCliente).collect(Collectors.toList());
    }
    public RespostaAvaliacao mapearRespostaAvaliacao(Integer nota, String comentario) {
        NotaAvaliacao notaVO = nota != null ? new NotaAvaliacao(nota) : null;
        ComentarioAvaliacao comentarioVO = comentario != null ? new ComentarioAvaliacao(comentario) : null;
        return new RespostaAvaliacao(notaVO, comentarioVO);
    }

    public AvaliacaoItemDTO mapearAvaliacaoItemDTO(AvaliacaoItem item) {
        return new AvaliacaoItemDTO(
                item.getId(),
                item.getProdutoId(),
                item.getNomeProdutoAvaliacao(),
                item.getNota() != null ? item.getNota().valor() : null,
                item.getComentarioAvaliacao() != null ? item.getComentarioAvaliacao().valor() : null
        );
    }

    public List<AvaliacaoItemDTO> mapearListaAvaliacaoItemDTO(List<AvaliacaoItem> itens) {
        return itens.stream().map(this::mapearAvaliacaoItemDTO).collect(Collectors.toList());
    }

    public AvaliacaoDTO mapearAvaliacaoDTO(Avaliacao avaliacao) {
        return new AvaliacaoDTO(
                avaliacao.getId(),
                avaliacao.getPedidoId(),
                avaliacao.getClienteId(),
                avaliacao.getStatus(),
                avaliacao.getAvaliacao(),
                avaliacao.getNota() != null ? avaliacao.getNota().valor() : null,
                avaliacao.getComentarioAvaliacao() != null ? avaliacao.getComentarioAvaliacao().valor() : null,
                avaliacao.getNumeroNotificacaoCliente(),
                avaliacao.getDataCriacao(),
                avaliacao.getDataExpiracao(),
                mapearListaAvaliacaoItemDTO(avaliacao.getItensAvaliados())
        );
    }

    public List<AvaliacaoDTO> mapearListaAvaliacoesDTO(List<Avaliacao> avaliacoes) {
        return avaliacoes.stream().map(this::mapearAvaliacaoDTO).collect(Collectors.toList());
    }

    public Map<Long, RespostaAvaliacao> mapearListaItensAvaliados(List<ItemAvaliadoDTO> itensAvaliados) {
        Map<Long, RespostaAvaliacao> mapa = new HashMap<>();
        for (ItemAvaliadoDTO itemAvaliado : itensAvaliados) {
            mapa.put(itemAvaliado.idDoItemAvaliado(), mapearRespostaAvaliacao(itemAvaliado.avaliacaoDTO().nota(), itemAvaliado.avaliacaoDTO().comentario()));
        }
        return mapa;
    }
}
