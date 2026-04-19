package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.UserDontFoundException;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.mapeador.OperadorMapper;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.validador.OperadorValidator;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarOperadorCasoDeUso {

    private final OperadorRepositorio repository;
    private final OperadorMapper mapper;
    private final OperadorValidator validator;

    public AtualizarOperadorCasoDeUso(OperadorRepositorio repository, OperadorMapper mapper, OperadorValidator validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Transactional
    public OperadorDTO executar(Long id, OperadorDTO dto) {
        Operador operadorExistente = repository.buscarPorId(id)
                .orElseThrow(() -> new UserDontFoundException("Operador não encontrado"));

        validator.validarAtualizacao(dto, operadorExistente);
        mapper.atualizarEntidade(operadorExistente, dto);
        repository.salvar(operadorExistente);

        return mapper.mapearOperadorParaOperadorDTO(operadorExistente);
    }
}