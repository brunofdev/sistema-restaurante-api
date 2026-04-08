package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.BuscarCardapioPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.saida.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.saida.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoNaoExisteException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.mapeador.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.validador.CardapioProdutoValidator;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.ObterProdutoPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioProdutoService {
    private final CardapioProdutoRepositorio cardapioProdutoRepositorio;
    private final CardapioProdutoMapper cardapioProdutoMapper;
    private final CardapioProdutoValidator cardapioProdutoValidator;
    private final BuscarCardapioPorIdCasoDeUso buscarCardapioPorId;
    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;

    @Autowired
    public CardapioProdutoService(CardapioProdutoRepositorio cardapioProdutoRepositorio,
                                  CardapioProdutoMapper cardapioProdutoMapper,
                                  ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso,
                                  CardapioProdutoValidator cardapioProdutoValidator,
                                  BuscarCardapioPorIdCasoDeUso buscarCardapioPorId){
        this.cardapioProdutoRepositorio = cardapioProdutoRepositorio;
        this.cardapioProdutoMapper = cardapioProdutoMapper;
        this.cardapioProdutoValidator = cardapioProdutoValidator;
        this.obterProdutoPorIdCasoDeUso = obterProdutoPorIdCasoDeUso;
        this.buscarCardapioPorId = buscarCardapioPorId;
    }

    public boolean verificarAssociacaoEntreProdutoCardapio(long idCardapio, long idProduto) {
        return cardapioProdutoRepositorio.encontrarProdutoCardapio(idCardapio, idProduto) >= 1;

    }
    public CardapioProduto encontrarUmCardapioComProduto (long idCardapio, long idProduto){
        return cardapioProdutoRepositorio.findByCardapioIdAndProdutoId(idCardapio, idProduto)
                .orElseThrow(() -> new AssociacaoNaoExisteException("Não existe associação entre o cardapio e o produto enviado"));
    }
    public List<CardapioComListaProdutoDTO> listarCardapiosProdutos() {
        return cardapioProdutoMapper.mapearCardapioComListaDeProduto(cardapioProdutoRepositorio.findAll());
    }
    public CardapioProdutoDTO listaUmCardapioComProduto(long idCardapio){
        CardapioProduto cardapioProduto = cardapioProdutoRepositorio.findByCardapioId(idCardapio)
                .orElseThrow(() -> new AssociacaoNaoExisteException("Não existe associação entre o cardápio e o produto enviado"));
        return cardapioProdutoMapper.mapearUmaEntidadeParaDTO(cardapioProduto);
    }
    public CardapioProdutoAssociacaoRespostaDTO criarAssociacaoProdutoCardapio(CardapioProdutoAssociacaoEntradaDTO dto) {
        cardapioProdutoValidator.validarCardapioProdutoAssociacaoEntradaDTO(dto, verificarAssociacaoEntreProdutoCardapio(dto.getIdCardapio(), dto.getIdProduto()), false);
        Produto produto = obterProdutoPorIdCasoDeUso.retornarEntidade(dto.getIdProduto());
        Cardapio cardapio = buscarCardapioPorId.executar(dto.getIdCardapio());
        CardapioProduto cardapioProduto = cardapioProdutoMapper.mapearCardapioProduto(produto, cardapio, dto);
        cardapioProdutoRepositorio.save(cardapioProduto);
        return cardapioProdutoMapper.mapearCardapioProdutoAssociacaoDTO(cardapioProduto);
    }
    public CardapioProdutoAssociacaoRespostaDTO atualizarCamposCustom(CardapioProdutoAssociacaoEntradaDTO dto){
        CardapioProduto cardapioProduto = encontrarUmCardapioComProduto(dto.getIdCardapio(), dto.getIdProduto());
        cardapioProdutoValidator.validarCardapioProdutoAssociacaoEntradaDTO(dto, verificarAssociacaoEntreProdutoCardapio(dto.getIdCardapio(), dto.getIdProduto()), true);
        CardapioProduto cardapioProdutoAtualizado = cardapioProdutoMapper.mapearCamposCustom(cardapioProduto, dto);
        cardapioProdutoRepositorio.save(cardapioProdutoAtualizado);
        return cardapioProdutoMapper.mapearCardapioProdutoAssociacaoDTO(cardapioProdutoAtualizado);
    }
    public void deletarAssociacaoCardapioProduto(long idCardapio, long idProduto){
        if (!verificarAssociacaoEntreProdutoCardapio(idCardapio, idProduto)) {
            throw new AssociacaoNaoExisteException("Não existe associação entre produto e cardapio");
        }
        cardapioProdutoRepositorio.deleteProdutoFromCardapio(idCardapio, idProduto);

    }














}