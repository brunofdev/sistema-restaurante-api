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
    public AssociacaoDTO mapearUmaEntidadeParaDTO (Associacao associacao) {
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
    public Associacao mapearUmaDtoParaEntidade (AssociacaoDTO associacaoDTO) {
        return new Associacao(
                associacaoDTO.getId(),
                cardapioMapeador.mapearUmaDtoParaEntidade(associacaoDTO.getCardapio()),
                produtoMapeador.mapearUmaDtoParaEntidade(associacaoDTO.getProduto()),
                associacaoDTO.getPrecoCustomizado(),
                associacaoDTO.getQuantidadeCustomizada(),
                associacaoDTO.getDescricaoCustomizada(),
                associacaoDTO.getDisponibilidadeCustomizada(),
                associacaoDTO.getObservacao()

        );
    }
    public AssociacaoFeitaRespostaDTO mapearCardapioProdutoAssociacaoDTO(Associacao associacao){
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

    public List<AssociacoesDTO> mapearCardapioComListaDeProduto(List<Associacao> listaDeAssociacao){
        Map<Long, AssociacoesDTO> map = new LinkedHashMap<>();
        for(Associacao cp : listaDeAssociacao){
            Cardapio cardapio = cp.getCardapio();
            AssociacoesDTO dto = map.computeIfAbsent(cardapio.getId(), id ->{
                return mapearCardapioProdutoDTO(cardapio);
        });
            Produto produto = cp.getProduto();
            ProdutoCustomDTO produtoCustomDTO = criarProdutoCustomDTO(produto, cp);
            dto.getProdutos().add(produtoCustomDTO);
    }
        return new ArrayList<>(map.values());
    }
    public Associacao mapearCardapioProduto(Produto produto, Cardapio cardapio, AssociacaoEntradaDTO associacaoEntradaDTO){
        return new Associacao(
                null,
                cardapio,
                produto,
                associacaoEntradaDTO.getPrecoCustomizado(),
                associacaoEntradaDTO.getQuantidadeCustomizada(),
                associacaoEntradaDTO.getDescricaoCustomizada(),
                associacaoEntradaDTO.getDisponibilidadeCustomizada(),
                associacaoEntradaDTO.getObservacao()
        );
    }
    public AssociacoesDTO mapearCardapioProdutoDTO(Cardapio cardapio) {
        AssociacoesDTO cardapioProdutoDTO = new AssociacoesDTO();
        cardapioProdutoDTO.setId(cardapio.getId());
        cardapioProdutoDTO.setNome(cardapio.getNome());
        cardapioProdutoDTO.setDescricao(cardapio.getDescricao());
        cardapioProdutoDTO.setDisponibilidade(cardapio.getDisponibilidade());
        cardapioProdutoDTO.setDataInicio(cardapio.getDataInicio());
        cardapioProdutoDTO.setDataFim(cardapio.getDataFim());
        cardapioProdutoDTO.setProdutos(new ArrayList<>());
        return cardapioProdutoDTO;
    }
    public ProdutoCustomDTO criarProdutoCustomDTO (Produto produto, Associacao associacao){
        ProdutoCustomDTO produtoCustomDTO = new ProdutoCustomDTO();
        produtoCustomDTO.setIdProduto(produto.getId());
        produtoCustomDTO.setNome(produto.getNome());
        produtoCustomDTO.setDescricao(produto.getDescricao());
        produtoCustomDTO.setPreco(produto.getPreco());
        produtoCustomDTO.setQuantidadeAtual(produto.getQuantidadeAtual());
        produtoCustomDTO.setDisponibilidade(produto.getDisponibilidade());
        produtoCustomDTO.setPrecoCustomizado(associacao.getPrecoCustomizado());
        produtoCustomDTO.setDescricaoCustomizada(associacao.getDescricaoCustomizada());
        produtoCustomDTO.setQuantidadeCustomizada(associacao.getQuantidadeCustomizada());
        produtoCustomDTO.setDisponibilidadeCustomizada(associacao.getDisponibilidadeCustomizada());
        produtoCustomDTO.setObservacao(associacao.getObservacao());
        return produtoCustomDTO;
    }
    public Associacao mapearCamposCustom(Associacao associacao, AssociacaoEntradaDTO dto){
        associacao.setPrecoCustomizado(dto.getPrecoCustomizado());
        associacao.setObservacao(dto.getObservacao());
        associacao.setDescricaoCustomizada(dto.getDescricaoCustomizada());
        associacao.setDisponibilidadeCustomizada(dto.getDisponibilidadeCustomizada());
        associacao.setQuantidadeCustomizada(dto.getQuantidadeCustomizada());
        return associacao;
    }


}
