package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.UserDontFoundException;
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
        return repository.buscarPorCpf(cpf).orElseThrow(
                () -> new UserDontFoundException("Operador não encontrado"));
    }
}