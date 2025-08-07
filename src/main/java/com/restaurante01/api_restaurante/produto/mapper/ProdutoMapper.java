package com.restaurante01.api_restaurante.produto.mapper;

import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {
    public static List<ProdutoDTO> converterVariosProdutos(List<Produto>produtos){
        List<ProdutoDTO> produtosDto = new ArrayList<>();
        for(Produto produto : produtos){
            ProdutoDTO produtoDTO = new ProdutoDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPreco(),
                    produto.getQuantidadeAtual(),
                    produto.getDisponibilidade()
            );
            produtosDto.add(produtoDTO);
        }
        return produtosDto;
    }
    public ProdutoDTO converterUmProduto(Produto produto){
         ProdutoDTO produtoConvertidoDTO = new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeAtual(),
                produto.getDisponibilidade()
        );
         return produtoConvertidoDTO;
    }
    public static Map<Long, ProdutoDTO> extrairIdsProdutosDTO(List<ProdutoDTO> loteProdutosDTO) {
        return loteProdutosDTO.stream()
                .collect(Collectors.toMap(ProdutoDTO::getId, dto -> dto));
    }
}
