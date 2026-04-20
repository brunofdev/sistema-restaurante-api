package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.UsuarioNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.mapeador.OperadorMapeador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.validador.OperadorValidador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarOperadorCasoDeUso {

    private final OperadorRepositorio repository;
    private final OperadorMapeador mapper;
    private final OperadorValidador validator;

    public AtualizarOperadorCasoDeUso(OperadorRepositorio repository, OperadorMapeador mapper, OperadorValidador validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    //atualizar precisa de ajuste, nao esta funcionando
    @Transactional
    public OperadorDTO executar(Long id, OperadorDTO dto) {
        Operador operadorExistente = repository.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoExcecao("Operador não encontrado"));

        validator.validarAtualizacao(dto, operadorExistente);
        mapper.atualizarEntidade(operadorExistente, dto);
        repository.salvar(operadorExistente);

        return mapper.mapearOperadorParaOperadorDTO(operadorExistente);
    }
}