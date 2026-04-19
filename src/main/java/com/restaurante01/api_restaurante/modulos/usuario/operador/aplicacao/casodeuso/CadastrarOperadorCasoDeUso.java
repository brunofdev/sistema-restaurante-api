package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.mapeador.OperadorMapper;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.validador.OperadorValidator;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.role.Role;
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