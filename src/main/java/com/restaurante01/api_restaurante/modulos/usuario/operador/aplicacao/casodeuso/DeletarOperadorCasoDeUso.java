package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.UserDontFoundException;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarOperadorCasoDeUso {

    private final OperadorRepositorio repository;

    public DeletarOperadorCasoDeUso(OperadorRepositorio repository) {
        this.repository = repository;
    }

    @Transactional
    public void executar(Long id) {
        Operador operador = repository.buscarPorId(id)
                .orElseThrow(() -> new UserDontFoundException("Operador não encontrado"));
        repository.deletar(operador);
    }
}