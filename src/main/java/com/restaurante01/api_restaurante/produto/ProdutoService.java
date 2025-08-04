package com.restaurante01.api_restaurante.produto;
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

    public List<ProdutoDTO> listarTodosProdutos() {
        return ProdutoParaProdutoDTO.converterVariosProdutos(produtoRepository.findAll());
    }

    public List<ProdutoDTO> listarProdutosDisponiveis(){
        return ProdutoParaProdutoDTO.converterVariosProdutos(produtoRepository.findByDisponibilidade(true));
    }

    public List<ProdutoDTO> listarProdutosIndisponiveis() {
        return ProdutoParaProdutoDTO.converterVariosProdutos(produtoRepository.findByDisponibilidade(false));
    }


    public List<ProdutoDTO> listarProdutosComQntdBaixa(){
        return ProdutoParaProdutoDTO.converterVariosProdutos(produtoRepository.findByQuantidadeAtualLessThan(11));
    }

    public ProdutoDTO adicionarNovoProduto(ProdutoDTO produtoRecebidoDTO) {
        if(produtoRecebidoDTO.getQuantidadeAtual() < 0 ){
            throw new IllegalArgumentException("A quantidade do produto não pode ser negativa");
        }
        Produto novoProduto = ProdutoFactory.instanciarProduto(produtoRecebidoDTO);

        Produto produtoSalvo = produtoRepository.save(novoProduto);

        return ProdutoParaProdutoDTO.converterUmProduto(produtoSalvo);
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
        return ProdutoParaProdutoDTO.converterVariosProdutos(produtosEncontrados);
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