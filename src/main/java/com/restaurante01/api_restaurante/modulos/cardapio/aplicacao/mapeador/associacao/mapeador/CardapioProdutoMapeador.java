package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.associacao.mapeador;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.CardapioMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacoesDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacaoFeitaRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.compartilhado.mapper_padrao_abstract.AbstractMapper;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.saida.ProdutoCustomDTO;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapeador;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardapioProdutoMapeador extends AbstractMapper<Associacao, AssociacaoDTO> {
    private final ProdutoMapeador produtoMapeador;
    private final CardapioMapeador cardapioMapeador;

    CardapioProdutoMapeador(ProdutoMapeador produtoMapeador, CardapioMapeador cardapioMapeador){
        this.produtoMapeador = produtoMapeador;
        this.cardapioMapeador = cardapioMapeador;
    }

    @Override
    public AssociacaoDTO mapearUmaEntidadeParaDTO(Associacao associacao) {
        return new AssociacaoDTO(
                associacao.getId(),
                cardapioMapeador.mapearUmaEntidadeParaDTO(associacao.getCardapio()),
                produtoMapeador.mapearUmaEntidadeParaDTO(associacao.getProduto()),
                associacao.getPrecoCustomizado(),
                associacao.getQuantidadeCustomizada(),
                associacao.getDescricaoCustomizada(),
                associacao.getDisponibilidadeCustomizada(),
                associacao.getObservacao()
        );
    }

    @Override
    public Associacao mapearUmaDtoParaEntidade(AssociacaoDTO associacaoDTO) {
        return new Associacao(
                associacaoDTO.id(),
                cardapioMapeador.mapearUmaDtoParaEntidade(associacaoDTO.cardapio()),
                produtoMapeador.mapearUmaDtoParaEntidade(associacaoDTO.produto()),
                associacaoDTO.precoCustomizado(),
                associacaoDTO.quantidadeCustomizada(),
                associacaoDTO.descricaoCustomizada(),
                associacaoDTO.disponibilidadeCustomizada(),
                associacaoDTO.observacao()
        );
    }

    public AssociacaoFeitaRespostaDTO mapearCardapioProdutoAssociacaoDTO(Associacao associacao) {
        return new AssociacaoFeitaRespostaDTO(
                "O produto: " + associacao.getProduto().getNome() + " foi associado ao " + associacao.getCardapio().getNome(),
                cardapioMapeador.mapearUmaEntidadeParaDTO(associacao.getCardapio()),
                produtoMapeador.mapearUmaEntidadeParaDTO(associacao.getProduto()),
                associacao.getPrecoCustomizado(),
                associacao.getQuantidadeCustomizada(),
                associacao.getDisponibilidadeCustomizada(),
                associacao.getObservacao(),
                associacao.getDescricaoCustomizada()
        );
    }

    public List<AssociacoesDTO> mapearCardapioComListaDeProduto(List<Associacao> listaDeAssociacao) {
        Map<Long, AssociacoesDTO> map = new LinkedHashMap<>();
        for (Associacao cp : listaDeAssociacao) {
            Cardapio cardapio = cp.getCardapio();
            AssociacoesDTO dto = map.computeIfAbsent(cardapio.getId(), id -> mapearCardapioProdutoDTO(cardapio));
            dto.produtos().add(criarProdutoCustomDTO(cp.getProduto(), cp));
        }
        return new ArrayList<>(map.values());
    }

    public Associacao mapearCardapioProduto(Produto produto, Cardapio cardapio, AssociacaoEntradaDTO dto) {
        return new Associacao(
                null,
                cardapio,
                produto,
                dto.precoCustomizado(),
                dto.quantidadeCustomizada(),
                dto.descricaoCustomizada(),
                dto.disponibilidadeCustomizada(),
                dto.observacao()
        );
    }

    public AssociacoesDTO mapearCardapioProdutoDTO(Cardapio cardapio) {
        return new AssociacoesDTO(
                cardapio.getId(),
                cardapio.getNome(),
                cardapio.getDescricao(),
                cardapio.getDisponibilidade(),
                cardapio.getDataInicio(),
                cardapio.getDataFim(),
                new ArrayList<>()
        );
    }

    public ProdutoCustomDTO criarProdutoCustomDTO(Produto produto, Associacao associacao) {
        return new ProdutoCustomDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeAtual(),
                produto.getDisponibilidade(),
                associacao.getPrecoCustomizado(),
                associacao.getQuantidadeCustomizada() != null ? associacao.getQuantidadeCustomizada() : 0,
                associacao.getDescricaoCustomizada(),
                associacao.getDisponibilidadeCustomizada(),
                associacao.getObservacao()
        );
    }

    public Associacao mapearCamposCustom(Associacao associacao, AssociacaoEntradaDTO dto) {
        associacao.setPrecoCustomizado(dto.precoCustomizado());
        associacao.setObservacao(dto.observacao());
        associacao.setDescricaoCustomizada(dto.descricaoCustomizada());
        associacao.setDisponibilidadeCustomizada(dto.disponibilidadeCustomizada());
        associacao.setQuantidadeCustomizada(dto.quantidadeCustomizada());
        return associacao;
    }
}
