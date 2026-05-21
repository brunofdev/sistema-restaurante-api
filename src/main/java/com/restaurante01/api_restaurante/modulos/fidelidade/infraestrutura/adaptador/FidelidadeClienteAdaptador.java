package com.restaurante01.api_restaurante.modulos.fidelidade.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.FidelidadeNaoEncontradaExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.repositorio.FidelidadeRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.porta.ClienteFidelidadePorta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class FidelidadeClienteAdaptador implements ClienteFidelidadePorta {

    private final FidelidadeRepositorio repositorio;

    @Override
    public int obterPontuacaoFidelidade(Long idCliente) {
        Fidelidade fidelidade = repositorio.buscarPorClienteId(idCliente)
                .orElseThrow(() -> new FidelidadeNaoEncontradaExcecao("Nao foi encontrado fidelidade para o cliente com id " + idCliente));
        return fidelidade.getPontuacaoAtual().valor();
    }

    @Override
    public Map<Long, Integer> obterListaDeFidelidade(List<Long> idsCliente) {
        List<Fidelidade> fidelidadesEncontradas = repositorio.buscarTodosPorListaDeIds(idsCliente);
        Map<Long, Integer> fidelidadesMapeadasPorIdEPontuacaoAtual = new HashMap<>();
        for(Fidelidade fidelidade : fidelidadesEncontradas){
            fidelidadesMapeadasPorIdEPontuacaoAtual.put(fidelidade.getClienteId(), fidelidade.getPontuacaoAtual().valor());
        }
        return fidelidadesMapeadasPorIdEPontuacaoAtual;
    }
}
