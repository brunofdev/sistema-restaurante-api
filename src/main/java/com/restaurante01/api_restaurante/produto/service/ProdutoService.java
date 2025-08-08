package com.restaurante01.api_restaurante.produto.service;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoNaoEncontradoException;
import com.restaurante01.api_restaurante.produto.factory.ProdutoFactory;
import com.restaurante01.api_restaurante.produto.mapper.ProdutoMapper;
import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.repository.ProdutoRepository;
import com.restaurante01.api_restaurante.produto.validator.ProdutoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoPossuiHistorico;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Set;



@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoValidator produtoValidator;
    private final ProdutoMapper produtoMapper;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, ProdutoValidator produtoValidator, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoValidator = produtoValidator;
        this.produtoMapper = produtoMapper;
    }
    public List<ProdutoDTO> listarTodosProdutos() {
        return produtoMapper.converterVariosProdutos(produtoRepository.findAll());
    }

    public List<ProdutoDTO> listarProdutosDisponiveis(){
        return produtoMapper.converterVariosProdutos(produtoRepository.findByDisponibilidade(true));
    }

    public List<ProdutoDTO> listarProdutosIndisponiveis() {
        return produtoMapper.converterVariosProdutos(produtoRepository.findByDisponibilidade(false));
    }
    public ProdutoDTO listarUmProdutoPorId(long id){
        return produtoMapper.converterUmProduto(produtoRepository.findById(id).orElseThrow(() ->
                new ProdutoNaoEncontradoException("Produto não encontrado")));
    }
    /*este método é de uso exclusivo para modificar estados no banco, como atualizar ou deletar um produto*/
    private Produto buscarProdutoPorId(long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));
    }


    public List<ProdutoDTO> listarProdutosComQntdBaixa(){
        return produtoMapper.converterVariosProdutos(produtoRepository.findByQuantidadeAtualLessThan(11));
    }

    public ProdutoDTO adicionarNovoProduto(ProdutoDTO produtoDTO) {
            produtoValidator.validarProduto(produtoDTO);
            Produto novoProduto = ProdutoFactory.instanciarProduto(produtoDTO);  /* */
            Produto produtoSalvo = produtoRepository.save(novoProduto);
            return produtoMapper.converterUmProduto(produtoSalvo);
        }
    private List<Produto> encontrarProdutos(Set<Long> idsMap){
        return produtoRepository.findAllById(idsMap);
    }

    public List<ProdutoDTO> atualizarLoteProdutos(List<ProdutoDTO> loteProdutosDTO){
        produtoValidator.validarListaDeProdutos(loteProdutosDTO);
        Map<Long, ProdutoDTO> idsMap = produtoMapper.extrairIdsProdutosDTO(loteProdutosDTO);
        List<Produto> produtosEncontrados = encontrarProdutos(idsMap.keySet());
        List<Produto>produtosAtualizados = ProdutoFactory.atualizarProdutoEmLote(idsMap, produtosEncontrados);
        produtoRepository.saveAll(produtosAtualizados);
        return produtoMapper.converterVariosProdutos(produtosAtualizados);
    }

    public ProdutoDTO atualizarProduto(long id, ProdutoDTO produtoAtualizado) {
        produtoValidator.validarProduto(produtoAtualizado);
        Produto produtoExistente = buscarProdutoPorId(id);
        ProdutoFactory.atualizarProduto(produtoExistente, produtoAtualizado);
        return produtoMapper.converterUmProduto(produtoRepository.save(produtoExistente));
    }

    public Produto deletarProduto(long id) {
        try {
            Produto produtoDeletado = buscarProdutoPorId(id);
            produtoRepository.delete(produtoDeletado);
            return produtoDeletado;
        } catch (DataIntegrityViolationException e) {
            throw new ProdutoPossuiHistorico("Este produto possui histórico/vinculo com ItensPedidos, se fosse excluido perderiamos os dados deste histórico");
        }
    }
}