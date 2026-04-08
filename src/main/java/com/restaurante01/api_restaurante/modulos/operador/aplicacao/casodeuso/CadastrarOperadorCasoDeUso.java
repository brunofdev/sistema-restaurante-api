package com.restaurante01.api_restaurante.modulos.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.operador.api.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.aplicacao.mapper.OperadorMapper;
import com.restaurante01.api_restaurante.modulos.operador.aplicacao.validador.OperadorValidator;
import com.restaurante01.api_restaurante.modulos.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.operador.dominio.repositorio.OperadorRepositorio;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarOperadorCasoDeUso {

    private final OperadorRepositorio repository;
    private final OperadorValidator validator;
    private final OperadorMapper mapper;

    public CadastrarOperadorCasoDeUso(OperadorRepositorio repository, OperadorValidator validator, OperadorMapper mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Transactional
    public OperadorDTO executar(CadastrarOperadorDTO dtoComSenhaEncoded) {
        validator.validarNovoOperador(dtoComSenhaEncoded, false);

        Operador operador = mapper.mappearNovoOperador(dtoComSenhaEncoded);
        operador.setRole(Role.ADMIN1); // Define o nível de acesso

        Operador operadorSalvo = repository.salvar(operador);
        return mapper.mapearOperadorParaOperadorDTO(operadorSalvo);
    }
}