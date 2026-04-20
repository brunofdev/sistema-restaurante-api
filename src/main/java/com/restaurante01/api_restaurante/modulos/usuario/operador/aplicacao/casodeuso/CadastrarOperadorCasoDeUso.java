package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.mapeador.OperadorMapeador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.validador.OperadorValidador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarOperadorCasoDeUso {

    private final OperadorRepositorio repository;
    private final OperadorValidador validator;
    private final OperadorMapeador mapper;

    public CadastrarOperadorCasoDeUso(OperadorRepositorio repository, OperadorValidador validator, OperadorMapeador mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Transactional
    public OperadorDTO executar(CadastrarOperadorDTO dtoComSenhaEncoded) {
        validator.validarNovoOperador(dtoComSenhaEncoded, false);
        Operador operador = mapper.mappearNovoOperador(dtoComSenhaEncoded);
        Operador operadorSalvo = repository.salvar(operador);
        return mapper.mapearOperadorParaOperadorDTO(operadorSalvo);
    }
}