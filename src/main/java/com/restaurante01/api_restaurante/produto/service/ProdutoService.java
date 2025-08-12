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
        return produtoMapper.mapearLoteParaDTO(produtoRepository.findAll());
    }

    public List<ProdutoDTO> listarProdutosDisponiveis(){
        return produtoMapper.mapearLoteParaDTO(produtoRepository.findByDisponibilidade(true));
    }

    public List<ProdutoDTO> listarProdutosIndisponiveis() {
        return produtoMapper.mapearLoteParaDTO(produtoRepository.findByDisponibilidade(false));
    }
    public ProdutoDTO listarUmProdutoPorId(long id){
        return produtoMapper.mapearEntityParaDTO(produtoRepository.findById(id).orElseThrow(() ->
                new ProdutoNaoEncontradoException("Produto não encontrado")));
    }
    private Produto buscarProdutoPorId(long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));
    }
    public List<ProdutoDTO> listarProdutosComQntdBaixa(){
        return produtoMapper.mapearLoteParaDTO(produtoRepository.findByQuantidadeAtualLessThan(11));
    }
    public ProdutoDTO adicionarNovoProduto(ProdutoDTO produtoDTO) {
            produtoValidator.validarProduto(produtoDTO);
            Produto novoProduto = ProdutoFactory.instanciarProduto(produtoDTO);  /* */
            Produto produtoSalvo = produtoRepository.save(novoProduto);
            return produtoMapper.mapearEntityParaDTO(produtoSalvo);
        }
    private List<Produto> buscarProdutosPorIds(Set<Long> idsMapeados){
        return produtoRepository.findAllById(idsMapeados);
    }
    public List<ProdutoDTO> atualizarLoteProdutos(List<ProdutoDTO> loteProdutosDTO){
        produtoValidator.validarListaDeProdutos(loteProdutosDTO);
        Map<Long, ProdutoDTO> mapaProdutosPorId = produtoMapper.extrairIdsProdutosDTO(loteProdutosDTO);
        List<Produto> produtosEncontrados = buscarProdutosPorIds(mapaProdutosPorId.keySet());
        List<Produto>produtosAtualizados = produtoMapper.atualizarProdutosEmLote(mapaProdutosPorId, produtosEncontrados);
        produtoRepository.saveAll(produtosAtualizados);
        return produtoMapper.mapearLoteParaDTO(produtosAtualizados);
    }
    public ProdutoDTO atualizarProduto(long id, ProdutoDTO produtoAtualizado) {
        produtoValidator.validarProduto(produtoAtualizado);
        Produto produtoExistente = buscarProdutoPorId(id);
        produtoMapper.atualizarProduto(produtoExistente, produtoAtualizado);
        return produtoMapper.mapearEntityParaDTO(produtoRepository.save(produtoExistente));
    }
    public ProdutoDTO deletarProduto(long id) {
        try {
            Produto produtoDeletado = buscarProdutoPorId(id);
            produtoRepository.delete(produtoDeletado);
            return produtoMapper.mapearEntityParaDTO(produtoDeletado);
        } catch (DataIntegrityViolationException e) {
            throw new ProdutoPossuiHistorico("Este produto possui histórico/vinculo com ItensPedidos, se fosse excluido perderiamos os dados deste histórico");
        }
    }
}