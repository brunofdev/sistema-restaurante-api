package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador.ClienteMapeador;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.validador.ClienteValidador; // Import adicionado
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.UsuarioNaoEncontradoExcecao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarClienteCasoDeUso {

    private final ClienteRepositorio repository;
    private final ClienteMapeador mapper;
    private final ClienteValidador validator;

    public AtualizarClienteCasoDeUso(ClienteRepositorio repository, ClienteMapeador mapper, ClienteValidador validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }
      //atualizar precisa de ajuste, nao esta funcionando
    @Transactional
    public ClienteDTO executar(Long id, ClienteDTO dto) {
       Cliente clienteExistente = repository.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoExcecao("Cliente não encontrado"));
        validator.validarAtualizacao(dto, clienteExistente);
        mapper.atualizarEntidade(clienteExistente, dto);
        repository.salvar(clienteExistente);

        return mapper.mapearClienteParaClienteDTO(clienteExistente);
    }
}