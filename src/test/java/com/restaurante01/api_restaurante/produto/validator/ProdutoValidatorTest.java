package com.restaurante01.api_restaurante.produto.validator;

import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoMesmoNomeExistenteException;
import com.restaurante01.api_restaurante.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProdutoValidatorTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoValidator produtoValidator;

    @Test
    void deveValidarProdutoSemErros(){
        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,"teste", "teste", 23.5, 15L, true);
        Produto produtoExistente = new Produto();
        when(produtoRepository.findByNome("teste")).thenReturn(null);
        assertDoesNotThrow(() -> produtoValidator.validarProduto(produtoDTO));
    }
    @Test
    void deveLancarExcecaoQuandoNomeDoProdutoJaExiste(){
        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,"teste", "teste", 23.5, 15L, true);
        Produto produtoExistente = new Produto();
        produtoExistente.setNome("teste");
        when(produtoRepository.findByNome("teste")).thenReturn(produtoExistente);
        assertThrows(ProdutoMesmoNomeExistenteException.class, () -> produtoValidator.validarProduto(produtoDTO));
        verify(produtoRepository).findByNome("teste");
        verifyNoMoreInteractions(produtoRepository);
    }
}























