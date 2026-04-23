package com.restaurante01.api_restaurante.modulos.produto.aplicacao.validador;
import com.restaurante01.api_restaurante.compartilhado.utils.formatadorstring.FormatarString;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoMesmoNomeExistenteException;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNomeInvalidoException;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoQntdNegativa;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.persistencia.ProdutoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class ProdutoValidador {

    private final ProdutoJPA produtoRepository ;

    public ProdutoValidador(ProdutoJPA produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    public void validarProduto(ProdutoDTO produtoDTO) {
        String nomeProduto = FormatarString.limparEspacos(produtoDTO.nome());
        Produto produtoExistente = produtoRepository.findByNome(nomeProduto);
        if(produtoDTO.quantidadeAtual() < 0){
            throw new ProdutoQntdNegativa("Quantidade não pode ser negativa");
        }
        if(produtoDTO.nome().length() <= 3){
            throw new ProdutoNomeInvalidoException("Nome **" + nomeProduto + "** deve possuir MAIS de três (3) caracteres");
        }
        if(produtoDTO.nome().length() >= 30){
            throw new ProdutoNomeInvalidoException("Nome **" + nomeProduto + "** deve possuir MENOS (30) caracteres");
        }
        //garante idempotência no verbo put do controlador
        if (produtoExistente != null) {
            if (produtoDTO.id() == null || !produtoExistente.getId().equals(produtoDTO.id())) {
                throw new ProdutoMesmoNomeExistenteException(
                        "Nome de Produto  **" + nomeProduto + "** já existe no sistema"
                );
            }
        }
    }
    public void validarListaDeProdutos(List<ProdutoDTO> produtosParaValidar){
        for(ProdutoDTO produtoDTO : produtosParaValidar){
            validarProduto(produtoDTO);
         }
    }
}
