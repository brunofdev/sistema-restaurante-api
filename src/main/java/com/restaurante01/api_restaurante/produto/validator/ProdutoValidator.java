package com.restaurante01.api_restaurante.produto.validator;
import com.restaurante01.api_restaurante.core.utils.FormatarString;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoMesmoNomeExistenteException;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoNomeInvalidoException;
import com.restaurante01.api_restaurante.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class ProdutoValidator {
    @Autowired
    private final ProdutoRepository produtoRepository ;

    public ProdutoValidator(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }
    public ProdutoRepository getProdutoRepository() {
        return produtoRepository;
    }
    public void validarProduto(ProdutoDTO produtoDTO) {
        String nomeProduto = FormatarString.limparEspacos(produtoDTO.getNome());
        Produto produtoExistente = produtoRepository.findByNome(nomeProduto);
        if (produtoExistente != null) {
            if (produtoDTO.getId() == null || !produtoExistente.getId().equals(produtoDTO.getId())) {
                throw new ProdutoMesmoNomeExistenteException(
                        "Nome de Produto  **" + nomeProduto + "** já existe no sistema"
                );
            }
        }
        if(produtoDTO.getNome().length() <= 3){
            throw new ProdutoNomeInvalidoException("Nome **" + nomeProduto + "** deve possuir MAIS de três (3) caracteres");
        }
        if(produtoDTO.getNome().length() >= 30){
            throw new ProdutoNomeInvalidoException("Nome **" + nomeProduto + "** deve possuir MENOS (30) caracteres");
        }
    }
    /*encontrado bug, este método é utilizado por enquanto, somente com o vergo put, que deve ser idempotente, ou seja
    //caso seja enviado um produto com o mesmo nome, ele deve realizar a operação com o mesmo nome de qualquer forma*/
    public void validarListaDeProdutos(List<ProdutoDTO> produtosParaValidar){
        for(ProdutoDTO produto : produtosParaValidar){
            validarProduto(produto);
         }
    }
}
