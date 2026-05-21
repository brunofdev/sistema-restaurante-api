package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.UsuarioNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObterClientePorCpfCasoDeUso {

    private final ClienteRepositorio repository;



    public Cliente retornarEntidade(String cpf) {
        return repository
                .buscarPorCpf(new Cpf(cpf))
                .orElseThrow(() -> new UsuarioNaoEncontradoExcecao("Cliente não encontrado com o CPF informado"));
    }

}