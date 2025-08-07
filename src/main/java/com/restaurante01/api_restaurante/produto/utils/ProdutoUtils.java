package com.restaurante01.api_restaurante.produto.utils;
import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ProdutoUtils {
    public static Map<Long, ProdutoDTO> extrairIdsProdutosDTO(List<ProdutoDTO> loteProdutosDTO) {
        return loteProdutosDTO.stream()
                .collect(Collectors.toMap(ProdutoDTO::getId, dto -> dto));
    }
}
