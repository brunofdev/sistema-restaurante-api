package com.restaurante01.api_restaurante;

<<<<<<< Updated upstream
import com.restaurante01.api_restaurante.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.entitys.Produto;
import com.restaurante01.api_restaurante.excepetions.PrecoProdutoNegativoException;
import com.restaurante01.api_restaurante.excepetions.ProdutoPossuiHistorico;
import com.restaurante01.api_restaurante.repository.ProdutoRepository;
import com.restaurante01.api_restaurante.services.ProdutoService;
=======
import com.restaurante01.api_restaurante.produto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.Produto;
import com.restaurante01.api_restaurante.produto.exceptions.PrecoProdutoNegativoException;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoPossuiHistorico;
import com.restaurante01.api_restaurante.produto.ProdutoRepository;
import com.restaurante01.api_restaurante.produto.ProdutoService;
>>>>>>> Stashed changes
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveSalvarProdutoComSucesso() {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Maçã", "Produto de teste", 15.48, 18, true);
        Produto produtoSalvo = new Produto(1L, "Maçã", "Produto de teste", 15.48, 18, true);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        ProdutoDTO resultado = produtoService.adicionarNovoProduto(produtoDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Maçã", resultado.getNome());
        assertEquals(15.48, resultado.getPreco());
        assertEquals(18, resultado.getQuantidadeAtual());
        assertTrue(resultado.getDisponibilidade());

        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void deveLancarExceptionSeQuantidadeNegativaAoSalvar() {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Pera", "Produto com qtd negativa", 10.0, -1, true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            produtoService.adicionarNovoProduto(produtoDTO);
        });
        assertEquals("A quantidade do produto não pode ser negativa", ex.getMessage());
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveListarTodosProdutos() {
        Produto p1 = new Produto(1L, "Maçã", "desc", 10.0, 10, true);
        Produto p2 = new Produto(2L, "Pera", "desc", 20.0, 20, false);

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<ProdutoDTO> produtos = produtoService.listarTodosProdutos();

        assertEquals(2, produtos.size());
        verify(produtoRepository).findAll();
    }

    @Test
    void deveListarProdutosDisponiveis() {
        Produto p1 = new Produto(1L, "Maçã", "desc", 10.0, 10, true);

        when(produtoRepository.findByDisponibilidade(true)).thenReturn(List.of(p1));

        List<ProdutoDTO> produtos = produtoService.listarProdutosDisponiveis();

        assertEquals(1, produtos.size());
        assertTrue(produtos.get(0).getDisponibilidade());
        verify(produtoRepository).findByDisponibilidade(true);
    }

    @Test
    void deveListarProdutosIndisponiveis() {
        Produto p1 = new Produto(2L, "Pera", "desc", 20.0, 20, false);

        when(produtoRepository.findByDisponibilidade(false)).thenReturn(List.of(p1));

        List<ProdutoDTO> produtos = produtoService.listarProdutosIndisponiveis();

        assertEquals(1, produtos.size());
        assertFalse(produtos.get(0).getDisponibilidade());
        verify(produtoRepository).findByDisponibilidade(false);
    }

    @Test
    void deveListarProdutosComQuantidadeBaixa() {
        Produto p1 = new Produto(1L, "Maçã", "desc", 10.0, 5, true);

        when(produtoRepository.findByQuantidadeAtualLessThan(11)).thenReturn(List.of(p1));

        List<ProdutoDTO> produtos = produtoService.listarProdutosComQntdBaixa();

        assertEquals(1, produtos.size());
        assertTrue(produtos.get(0).getQuantidadeAtual() < 11);
        verify(produtoRepository).findByQuantidadeAtualLessThan(11);
    }

    /* @Test
   void deveAtualizarVariosProdutosComSucesso() {
        ProdutoDTO dto1 = new ProdutoDTO(1L, "Maçã Atualizada", "desc nova", 12.0, 10, true);
        ProdutoDTO dto2 = new ProdutoDTO(2L, "Pera Atualizada", "desc nova", 22.0, 5, false);

        Produto p1 = new Produto(1L, "Maçã", "desc", 10.0, 10, true);
        Produto p2 = new Produto(2L, "Pera", "desc", 20.0, 5, false);

        when(produtoRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(p1, p2));
        when(produtoRepository.saveAll(anyList())).thenReturn(List.of(p1, p2));

        List<ProdutoDTO> atualizados = produtoService.atualizarVariosProdutos(List.of(dto1, dto2));

        assertEquals(2, atualizados.size());
        assertEquals("Maçã Atualizada", atualizados.get(0).getNome());
        assertEquals("Pera Atualizada", atualizados.get(1).getNome());

        verify(produtoRepository).findAllById(List.of(1L, 2L));
        verify(produtoRepository).saveAll(anyList());
    }
*/
    @Test
    void deveAtualizarProdutoComSucesso() {
        Produto produtoOriginal = new Produto(1L, "Maçã", "desc", 10.0, 10, true);
        Produto produtoAtualizado = new Produto(null, "Maçã Atualizada", "desc nova", 15.0, 20, false);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoOriginal));
        when(produtoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = produtoService.atualizarProduto(1L, produtoAtualizado);

        assertEquals("Maçã Atualizada", resultado.getNome());
        assertEquals(15.0, resultado.getPreco());
        assertEquals(20, resultado.getQuantidadeAtual());
        assertFalse(resultado.getDisponibilidade());

        verify(produtoRepository).findById(1L);
        verify(produtoRepository).save(produtoOriginal);
    }

    @Test
    void deveLancarExceptionAoAtualizarProdutoComPrecoNegativo() {
        Produto produtoOriginal = new Produto(1L, "Maçã", "desc", 10.0, 10, true);
        Produto produtoAtualizado = new Produto(null, "Maçã", "desc", -10.0, 10, true);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoOriginal));

        PrecoProdutoNegativoException ex = assertThrows(PrecoProdutoNegativoException.class, () -> {
            produtoService.atualizarProduto(1L, produtoAtualizado);
        });

        assertEquals("Preço não pode ser negativo", ex.getMessage());
        verify(produtoRepository).findById(1L);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarExceptionAoAtualizarProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            produtoService.atualizarProduto(1L, new Produto());
        });

        assertEquals("Produto não encontrado", ex.getMessage());
        verify(produtoRepository).findById(1L);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveDeletarProdutoComSucesso() {
        Produto produto = new Produto(1L, "Maçã", "desc", 10.0, 10, true);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).delete(produto);

        Produto deletado = produtoService.deletarProduto(1L);

        assertEquals(produto, deletado);
        verify(produtoRepository).findById(1L);
        verify(produtoRepository).delete(produto);
    }

    @Test
    void deveLancarProdutoPossuiHistoricoAoDeletar() {
        Produto produto = new Produto(1L, "Maçã", "desc", 10.0, 10, true);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        doThrow(DataIntegrityViolationException.class).when(produtoRepository).delete(produto);

        ProdutoPossuiHistorico ex = assertThrows(ProdutoPossuiHistorico.class, () -> {
            produtoService.deletarProduto(1L);
        });

        assertTrue(ex.getMessage().contains("Este produto possui histórico/vinculo"));
        verify(produtoRepository).findById(1L);
        verify(produtoRepository).delete(produto);
    }

    @Test
    void deveLancarRuntimeExceptionAoDeletarProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            produtoService.deletarProduto(1L);
        });

        assertEquals("Produto não encontrado", ex.getMessage());
        verify(produtoRepository).findById(1L);
        verify(produtoRepository, never()).delete(any());
    }
}
