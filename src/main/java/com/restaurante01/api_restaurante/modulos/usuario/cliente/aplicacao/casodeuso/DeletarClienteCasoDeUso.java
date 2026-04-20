package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.UsuarioNaoEncontradoExcecao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarClienteCasoDeUso {

    private final ClienteRepositorio repository;

    public DeletarClienteCasoDeUso(ClienteRepositorio repository) {
        this.repository = repository;
    }

    @Transactional
    public void executar(Long id) {
        Cliente cliente = repository.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoExcecao("Cliente não encontrado"));
        repository.deletar(cliente);
    }
}