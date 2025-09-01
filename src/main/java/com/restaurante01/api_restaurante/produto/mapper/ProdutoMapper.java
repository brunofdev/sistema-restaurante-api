package com.restaurante01.api_restaurante.produto.mapper;

import com.restaurante01.api_restaurante.core.mapper.AbstractMapper;
import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper extends AbstractMapper<Produto, ProdutoDTO> {
   @Override
    public ProdutoDTO mapearUmaEntidadeParaDTO(Produto produto){
         return new ProdutoDTO(
                produto.getId(),
                produto.getNome().trim().replaceAll("\\s+", " "),
                produto.getDescricao().trim().replaceAll("\\s+", " "),
                produto.getPreco(),
                produto.getQuantidadeAtual(),
                produto.getDisponibilidade()
        );
    }
    @Override
    public Produto mapearUmaDtoParaEntidade(ProdutoDTO produtoDTO) {
        return new Produto(
                produtoDTO.getId(),
                produtoDTO.getNome().trim().replaceAll("\\s+", " "),
                produtoDTO.getDescricao().trim().replaceAll("\\s+", " "),
                produtoDTO.getPreco(),
                produtoDTO.getQuantidadeAtual(),
                produtoDTO.getDisponibilidade()
        );
    }
    public Map<Long, ProdutoDTO> mapearIdsEntidadeParaDTO(List<ProdutoDTO> loteProdutosDTO) {
        return loteProdutosDTO.stream()
                .collect(Collectors.toMap(ProdutoDTO::getId, dto -> dto));
    }
    public  void atualizarProduto(Produto produto, ProdutoDTO dto) {
        produto.setNome(dto.getNome().trim().replaceAll("\\s+", " "));
        produto.setDescricao(dto.getDescricao().trim().replaceAll("\\s+", " "));
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeAtual(dto.getQuantidadeAtual());
        produto.setDisponibilidade(dto.getDisponibilidade());
    }
    public  List<Produto> atualizarProdutosEmLote (Map<Long, ProdutoDTO> idsMapeados, List<Produto> produtosEncontrados){
        for (Produto produto : produtosEncontrados) {
            ProdutoDTO produtoAtualizado = idsMapeados.get(produto.getId());
            atualizarProduto(produto, produtoAtualizado);
        }
        return produtosEncontrados;
    }
}
