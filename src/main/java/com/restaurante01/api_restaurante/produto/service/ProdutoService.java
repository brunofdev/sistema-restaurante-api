package com.restaurante01.api_restaurante.produto.service;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoQntdNegativa;
import com.restaurante01.api_restaurante.produto.factory.ProdutoFactory;
import com.restaurante01.api_restaurante.produto.mapper.ProdutoMapper;
import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.repository.ProdutoRepository;
import com.restaurante01.api_restaurante.produto.validator.ProdutoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante01.api_restaurante.produto.exceptions.PrecoProdutoNegativoException;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoPossuiHistorico;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    public ProdutoRepository produtoRepository;
    public ProdutoValidator produtoValidator;

    public List<ProdutoDTO> listarTodosProdutos() {
        return ProdutoMapper.converterVariosProdutos(produtoRepository.findAll());
    }

    public List<ProdutoDTO> listarProdutosDisponiveis(){
        return ProdutoMapper.converterVariosProdutos(produtoRepository.findByDisponibilidade(true));
    }

    public List<ProdutoDTO> listarProdutosIndisponiveis() {
        return ProdutoMapper.converterVariosProdutos(produtoRepository.findByDisponibilidade(false));
    }


    public List<ProdutoDTO> listarProdutosComQntdBaixa(){
        return ProdutoMapper.converterVariosProdutos(produtoRepository.findByQuantidadeAtualLessThan(11));
    }

    public ProdutoDTO adicionarNovoProduto(ProdutoDTO produtoDTO) {
            produtoValidator.validarProduto(produtoDTO);
            Produto novoProduto = ProdutoFactory.instanciarProduto(produtoDTO);  /* */
            Produto produtoSalvo = produtoRepository.save(novoProduto);
            return ProdutoMapper.converterUmProduto(produtoSalvo);
        }

    public List<ProdutoDTO> atualizarDiversosProdutos(List<ProdutoDTO> produtosParaAtualizarDTO) {
        Map<Long, ProdutoDTO> idsMap = produtosParaAtualizarDTO.stream()
                .collect(Collectors.toMap(ProdutoDTO::getId, dto -> dto));
        List<Produto> produtosEncontrados = produtoRepository.findAllById(idsMap.keySet());
        for (Produto produto : produtosEncontrados) {
            ProdutoDTO produtoAtualizado = idsMap.get(produto.getId());
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setDisponibilidade(produtoAtualizado.getDisponibilidade());
            produto.setQuantidadeAtual(produtoAtualizado.getQuantidadeAtual());
        }
        produtoRepository.saveAll(produtosEncontrados);
        return ProdutoMapper.converterVariosProdutos(produtosEncontrados);
    }

        public Produto atualizarProduto(long id, Produto produtoAtualizado) {
        Produto produtoModificado = produtoRepository.findById(id)
                  .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            if (produtoAtualizado.getPreco() < 0) {
            throw new PrecoProdutoNegativoException("Preço não pode ser negativo");
        }
        produtoModificado.setNome(produtoAtualizado.getNome());
        produtoModificado.setDescricao(produtoAtualizado.getDescricao());
        produtoModificado.setPreco(produtoAtualizado.getPreco());
        produtoModificado.setQuantidadeAtual(produtoAtualizado.getQuantidadeAtual());
        produtoModificado.setDisponibilidade(produtoAtualizado.getDisponibilidade());
        return produtoRepository.save(produtoModificado);
    }

    public Produto deletarProduto(long id) {
        try {
            Produto produtoSerDeletado = produtoRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Produto não encontrado"));
            produtoRepository.delete(produtoSerDeletado);
            return produtoSerDeletado;
        } catch (DataIntegrityViolationException e) {
            throw new ProdutoPossuiHistorico("Este produto possui histórico/vinculo com ItensPedidos, se fosse excluido perderiamos os dados deste histórico");
        }
    }
}