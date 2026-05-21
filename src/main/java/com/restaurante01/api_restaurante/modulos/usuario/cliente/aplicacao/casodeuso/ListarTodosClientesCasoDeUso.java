package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador.ClienteMapeador;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.porta.ClienteFidelidadePorta;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ListarTodosClientesCasoDeUso {

    private final ClienteRepositorio repository;
    private final ClienteMapeador mapper;
    private final ClienteFidelidadePorta clienteFidelidadePorta;


    public Page<ClienteDTO> executar(Pageable pageable) {
        Page<Cliente> clientes =  repository.buscarTodos(pageable);
        List<Long> idsClientes = clientes.getContent()
                .stream()
                .map(Cliente::getId)
                .toList();
        Map<Long, Integer> idClientesComSuaFidelidade = clienteFidelidadePorta.obterListaDeFidelidade(idsClientes);
        return mapper.mapearListaClienteParaClienteDTO(clientes, idClientesComSuaFidelidade);
    }
}