package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.UsuarioNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.stereotype.Service;

@Service
public class ObterOperadorPorCpfCasoDeUso {

    private final OperadorRepositorio repository;

    public ObterOperadorPorCpfCasoDeUso(OperadorRepositorio repository) {
        this.repository = repository;
    }

    public Operador executar(String cpf) {
        return repository.buscarPorCpf(new Cpf(cpf)).orElseThrow(
                () -> new UsuarioNaoEncontradoExcecao("Operador não encontrado"));
    }
}