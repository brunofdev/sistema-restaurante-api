package com.restaurante01.api_restaurante.modulos.usuario.cliente.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.porta.FidelidadeClientePorta;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.excecao.ClienteNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteFidelidadeAdaptador implements FidelidadeClientePorta {

    private final ClienteRepositorio clienteRepositorio;

    @Override
    public void atualizarReferencia(Long clienteId, Long fidelidadeId) {
        Cliente cliente = clienteRepositorio.buscarPorId(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontradoExcecao("Cliente não encontrado com id: " + clienteId));
        cliente.vincularFidelidade(fidelidadeId);
        clienteRepositorio.salvar(cliente);
    }
}
