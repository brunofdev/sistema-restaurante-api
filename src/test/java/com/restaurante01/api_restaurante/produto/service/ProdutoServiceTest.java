package com.restaurante01.api_restaurante.produto.service;

import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoCreateDTO;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoNomeInvalidoException;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoQntdNegativa;
import com.restaurante01.api_restaurante.produto.mapper.ProdutoMapper;
import com.restaurante01.api_restaurante.produto.repository.ProdutoRepository;
import com.restaurante01.api_restaurante.produto.validator.ProdutoValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @Mock
    private ProdutoValidator produtoValidator;
    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void quandoCriarNovoProduto_ComDadosValidos_DeveSalverEretornarProdutoDTO(){
        ProdutoCreateDTO dadosEntrada = new ProdutoCreateDTO("churrus", "testando a descricao",
                new BigDecimal(26.5), 15L, true);
        ProdutoDTO dto = new ProdutoDTO(1L, "churrus", "testando a descricao",
                new BigDecimal(26.5), 15l, true, null, null, null, null);
        Produto novoProduto = new Produto();
        Produto produtoSalvo = new Produto();

        when(produtoMapper.mapearProdutoDTO(any(ProdutoCreateDTO.class))).thenReturn(dto);

        doNothing().when(produtoValidator).validarProduto(any());

        when(produtoMapper.mapearUmaDtoParaEntidade(any(ProdutoDTO.class))).thenReturn(novoProduto);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);
        when(produtoMapper.mapearUmaEntidadeParaDTO(any(Produto.class))).thenReturn(dto);

        ProdutoDTO resultado = produtoService.adicionarNovoProduto(dadosEntrada);

        assertNotNull(resultado);
        assertEquals("churrus", resultado.getNome());

        verify(produtoMapper, times(1)).mapearProdutoDTO(any(ProdutoCreateDTO.class));
        verify(produtoValidator, times(1)).validarProduto(any());
        verify(produtoMapper, times(1)).mapearUmaDtoParaEntidade(any(ProdutoDTO.class));
        verify(produtoRepository, times(1)).save(any(Produto.class));
        verify(produtoMapper, times(1)).mapearUmaEntidadeParaDTO(any(Produto.class));
    }

    @Test
    void deveLancarExceptionQuandoValidatorFalharAoCadastrarProduto() {
        ProdutoCreateDTO entrada = new ProdutoCreateDTO("ab", "desc", new BigDecimal(10.0), 10L, true);
        ProdutoDTO dtoMapeado = new ProdutoDTO(null, "ab", "desc", new BigDecimal(10.0), 10L, true, null, null, null, null);

        when(produtoMapper.mapearProdutoDTO(any())).thenReturn(dtoMapeado);

        doThrow(new ProdutoNomeInvalidoException("erro"))
                .when(produtoValidator).validarProduto(dtoMapeado);

        assertThrows(ProdutoNomeInvalidoException.class,
                () -> produtoService.adicionarNovoProduto(entrada));

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarProdutoQntdNegativa() {
        ProdutoCreateDTO entrada = new ProdutoCreateDTO("nome", "desc", new BigDecimal(10.0), 1L, true);
        ProdutoDTO dto = new ProdutoDTO(null, "nome", "desc", new BigDecimal(10.0), 1L, true, null, null, null, null);

        when(produtoMapper.mapearProdutoDTO(any())).thenReturn(dto);

        doThrow(new ProdutoQntdNegativa("qntd negativa"))
                .when(produtoValidator).validarProduto(dto);

        assertThrows(ProdutoQntdNegativa.class,
                () -> produtoService.adicionarNovoProduto(entrada));

        verify(produtoRepository, never()).save(any());
    }
}
