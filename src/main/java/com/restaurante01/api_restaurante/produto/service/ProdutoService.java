package com.restaurante01.api_restaurante.produto.service;
import com.restaurante01.api_restaurante.core.utils.FormatarString;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoCreateDTO;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoNaoEncontradoException;
import com.restaurante01.api_restaurante.produto.factory.ProdutoFactory;
import com.restaurante01.api_restaurante.produto.mapper.ProdutoMapper;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.repository.ProdutoRepository;
import com.restaurante01.api_restaurante.produto.validator.ProdutoValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
        return produtoMapper.mapearListaDeEntidadeParaDTO(produtoRepository.findAll());
    }
    public List<ProdutoDTO> listarProdutosDisponiveis(){
        return produtoMapper.mapearListaDeEntidadeParaDTO(produtoRepository.findByDisponibilidade(true));
    }
    public List<ProdutoDTO> listarProdutosIndisponiveis() {
        return produtoMapper.mapearListaDeEntidadeParaDTO(produtoRepository.findByDisponibilidade(false));
    }
    public ProdutoDTO listarUmProdutoPorId(long id){
        return produtoMapper.mapearUmaEntidadeParaDTO(produtoRepository.findById(id).orElseThrow(() ->
                new ProdutoNaoEncontradoException("Produto não encontrado")));
    }
    public boolean encontrarPorNome(String nome){
        return produtoRepository.existsByNome(nome);
    }
    public Produto buscarProdutoPorId(long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));
    }
    public List<ProdutoDTO> listarProdutosComQntdBaixa(){
        return produtoMapper.mapearListaDeEntidadeParaDTO(produtoRepository.findByQuantidadeAtualLessThan(11));
    }
    public ProdutoDTO adicionarNovoProduto(ProdutoCreateDTO produtoCreateDTO) {
            ProdutoDTO produtoDTO = produtoMapper.mapearProdutoDTO(produtoCreateDTO);
            produtoValidator.validarProduto(produtoDTO);
            Produto novoProduto = produtoMapper.mapearUmaDtoParaEntidade(produtoDTO);  /* */
            Produto produtoSalvo = produtoRepository.save(novoProduto);
            return produtoMapper.mapearUmaEntidadeParaDTO(produtoSalvo);
        }
    private List<Produto> buscarProdutosPorIds(Set<Long> idsMapeados){
        return produtoRepository.findAllById(idsMapeados);
    }
    public ProdutoDTO atualizarProduto(ProdutoDTO produtoAtualizado) {
        produtoValidator.validarProduto(produtoAtualizado);
        Produto produtoExistente = buscarProdutoPorId(produtoAtualizado.getId());
        produtoMapper.atualizarProduto(produtoExistente, produtoAtualizado);
        return produtoMapper.mapearUmaEntidadeParaDTO(produtoRepository.save(produtoExistente));
    }
    public List<ProdutoDTO> atualizarLoteProdutos(List<ProdutoDTO> loteProdutosDTO){
        produtoValidator.validarListaDeProdutos(loteProdutosDTO);
        Map<Long, ProdutoDTO> mapaProdutosPorId = produtoMapper.mapearIdsEntidadeParaDTO(loteProdutosDTO);
        List<Produto> produtosEncontrados = buscarProdutosPorIds(mapaProdutosPorId.keySet());
        List<Produto>produtosAtualizados = produtoMapper.atualizarProdutosEmLote(mapaProdutosPorId, produtosEncontrados);
        produtoRepository.saveAll(produtosAtualizados);
        return produtoMapper.mapearListaDeEntidadeParaDTO(produtosAtualizados);
    }
    public void deletarProduto(long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com id " + id + " não encontrado"));;
        produtoRepository.delete(produto);
    }
}