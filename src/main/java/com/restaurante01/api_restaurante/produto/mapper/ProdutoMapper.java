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
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeAtual(),
                produto.getDisponibilidade()
        );
    }
    public Map<Long, ProdutoDTO> mapearIdsEntidadeParaDTO(List<ProdutoDTO> loteProdutosDTO) {
        return loteProdutosDTO.stream()
                .collect(Collectors.toMap(ProdutoDTO::getId, dto -> dto));
    }
    public  void atualizarProduto(Produto produto, ProdutoDTO dto) {
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
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
