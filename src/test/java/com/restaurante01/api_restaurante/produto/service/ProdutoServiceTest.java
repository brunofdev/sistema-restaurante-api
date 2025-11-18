package com.restaurante01.api_restaurante.produto.service;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoCreateDTO;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoNaoEncontradoException;
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

import java.util.Optional;

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


    /*
    Testes do metodo:
    ProdutoDTO adicionarNovoProduto(ProdutoCreateDTO produtoCreateDTO)
    */
    @Test
    void quandoCriarNovoProduto_ComDadosValidos_DeveSalverEretornarProdutoDTO(){
        ProdutoCreateDTO dadosEntrada = new ProdutoCreateDTO("churrus", "testando a descricao",
                26.5, 15L, true);
        ProdutoDTO dto = new ProdutoDTO(1L, "churrus", "testando a descricao",
                26.5, 15l, true);
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
        ProdutoCreateDTO entrada = new ProdutoCreateDTO("ab", "desc", 10.0, 10L, true);
        ProdutoDTO dtoMapeado = new ProdutoDTO(null, "ab", "desc", 10.0, 10L, true);

        when(produtoMapper.mapearProdutoDTO(any())).thenReturn(dtoMapeado);

        doThrow(new ProdutoNomeInvalidoException("erro"))
                .when(produtoValidator).validarProduto(dtoMapeado);

        assertThrows(ProdutoNomeInvalidoException.class,
                () -> produtoService.adicionarNovoProduto(entrada));

        verify(produtoRepository, never()).save(any());
    }
    @Test
    void deveLancarProdutoQntdNegativa() {
        ProdutoCreateDTO entrada = new ProdutoCreateDTO("nome", "desc", 10.0, 1L, true);
        ProdutoDTO dto = new ProdutoDTO(null, "nome", "desc", 10.0, 1L, true);

        when(produtoMapper.mapearProdutoDTO(any())).thenReturn(dto);

        doThrow(new ProdutoQntdNegativa("qntd negativa"))
                .when(produtoValidator).validarProduto(dto);

        assertThrows(ProdutoQntdNegativa.class,
                () -> produtoService.adicionarNovoProduto(entrada));

        verify(produtoRepository, never()).save(any());
    }

    /*
    Testes do metodo:
    ProdutoDTO listarUmProdutoPorId(long id)
    */
    @Test
    void deveRetornarProdutoDTOQuandoProdutoExistir() {
        // ARRANGE
        long id = 1L;

        Produto produto = new Produto();
        produto.setId(id);

        ProdutoDTO produtoDTO = new ProdutoDTO(id, "Teste", "Desc", 10.0, 5L, true);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        when(produtoMapper.mapearUmaEntidadeParaDTO(produto)).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.listarUmProdutoPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(produtoRepository).findById(id);
        verify(produtoMapper).mapearUmaEntidadeParaDTO(produto);
    }
    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir(){
        long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ProdutoNaoEncontradoException.class,
                () -> produtoService.listarUmProdutoPorId(id));
        verify(produtoMapper, never()).mapearUmaEntidadeParaDTO(any());
    }

    /*
    Testes do metodo:
    ProdutoDTO atualizarProduto(ProdutoDTO produtoAtualizado)
    */
    @Test
    void deveAtualizarUmProdutoExistente(){
        // ---------- ARRANGE ----------
        ProdutoDTO produtoAtualizado = new ProdutoDTO(
                1L, "Novo Nome", "Nova Desc", 50.0, 10L, true
        );

        Produto produtoExistente = new Produto(
                1L, "Nome Antigo", "Desc Antiga", 30.0, 5L, false
        );

        Produto produtoDepoisAtualizacao = new Produto(
                1L, "Novo Nome", "Nova Desc", 50.0, 10L, true
        );

        ProdutoDTO produtoRetornadoMapper = new ProdutoDTO(
                1L, "Novo Nome", "Nova Desc", 50.0, 10L, true
        );

        doNothing().when(produtoValidator).validarProduto(any());
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));

        // atualizarProduto() é void → simulamos comportamento
        doAnswer(invoc -> {
            Produto entidade = invoc.getArgument(0);
            ProdutoDTO dto = invoc.getArgument(1);

            entidade.setNome(dto.getNome());
            entidade.setDescricao(dto.getDescricao());
            entidade.setPreco(dto.getPreco());
            entidade.setQuantidadeAtual(dto.getQuantidadeAtual());
            entidade.setDisponibilidade(dto.getDisponibilidade());
            return null;
        }).when(produtoMapper).atualizarProduto(any(), any());

        // save deve retornar um produto
        when(produtoRepository.save(produtoExistente))
                .thenReturn(produtoDepoisAtualizacao);

        when(produtoMapper.mapearUmaEntidadeParaDTO(produtoDepoisAtualizacao))
                .thenReturn(produtoRetornadoMapper);

        ProdutoDTO resultado = produtoService.atualizarProduto(produtoAtualizado);

        // ---------- ASSERT ----------
        assertNotNull(resultado);
        assertEquals("Novo Nome", resultado.getNome());
        assertEquals("Nova Desc", resultado.getDescricao());
        assertEquals(50.0, resultado.getPreco());
        assertEquals(10L, resultado.getQuantidadeAtual());
        assertTrue(resultado.getDisponibilidade());


        // ---------- VERIFY ----------
        verify(produtoValidator).validarProduto(produtoAtualizado);
        verify(produtoRepository).findById(1L);
        verify(produtoMapper).atualizarProduto(produtoExistente, produtoAtualizado);
        verify(produtoRepository).save(produtoExistente);
        verify(produtoMapper).mapearUmaEntidadeParaDTO(produtoDepoisAtualizacao);
    }









}
