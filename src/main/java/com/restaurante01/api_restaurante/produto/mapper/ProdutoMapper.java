package com.restaurante01.api_restaurante.produto.mapper;

import com.restaurante01.api_restaurante.core.utils.mapper_padrao_abstract.AbstractMapper;
import com.restaurante01.api_restaurante.core.utils.formatadores.FormatarString;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoCreateDTO;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper extends AbstractMapper<Produto, ProdutoDTO> {
   @Override
    public ProdutoDTO mapearUmaEntidadeParaDTO(Produto produto){
         return new ProdutoDTO(produto.getId(),
                 FormatarString.limparEspacos(produto.getNome()),
                 FormatarString.limparEspacos(produto.getDescricao()),
                 produto.getPreco(),
                 produto.getQuantidadeAtual(),
                 produto.getDisponibilidade(),
                 produto.getDataCriacao(),
                 produto.getDataAtualizacao(),
                 produto.getCriadoPor(),
                 produto.getAtualizadoPor()
        );
    }
    @Override
    public Produto mapearUmaDtoParaEntidade(ProdutoDTO produtoDTO) {
        return new Produto(
                produtoDTO.getId(),
                FormatarString.limparEspacos(produtoDTO.getNome()),
                FormatarString.limparEspacos(produtoDTO.getDescricao()),
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
        produto.setNome(FormatarString.limparEspacos(dto.getNome()));
        produto.setDescricao(FormatarString.limparEspacos(dto.getDescricao()));
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
    public ProdutoDTO mapearProdutoDTO(ProdutoCreateDTO produtoCreateDTO){
       return new ProdutoDTO(
               null,
               FormatarString.limparEspacos(produtoCreateDTO.getNome()),
               FormatarString.limparEspacos(produtoCreateDTO.getDescricao()),
               produtoCreateDTO.getPreco(),
               produtoCreateDTO.getQuantidadeAtual(),
               produtoCreateDTO.getDisponibilidade(),
               null,
               null,
               null,
               null
       );
    }
}
