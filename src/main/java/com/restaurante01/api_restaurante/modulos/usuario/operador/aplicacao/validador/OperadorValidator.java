package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.validador;

import com.restaurante01.api_restaurante.compartilhado.utils.validadorcpf.ValidadorCpf;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.CpfAlreadyExistsException;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.EmailAlreadyExistsException;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.UsernameAlreadyExistsException;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import org.springframework.stereotype.Component;

@Component
public class OperadorValidator {

    private final OperadorRepositorio operadorRepositorio;

    public OperadorValidator(OperadorRepositorio operadorRepositorio) {
        this.operadorRepositorio = operadorRepositorio;
    }

    public void validarNovoOperador(CadastrarOperadorDTO dto, Boolean isUpdate) {
        ValidadorCpf.validarCpf(dto.cpf());

        if (!isUpdate) {
            checaEmailExiste(dto.email());
            checaCpfExiste(dto.cpf());
        }
    }

    public void validarAtualizacao(OperadorDTO dto, Operador operadorExistente) {
        if (dto.cpf() != null && !dto.cpf().equals(operadorExistente.getUsername())) {
            checaCpfExiste(dto.cpf());
            checaCpfExiste(dto.cpf());
        }
    }

    private void checaEmailExiste(String email){
        if (operadorRepositorio.existePorEmail(email)) {
            throw new EmailAlreadyExistsException("Email já cadastrado no sistema");
        }
    }

    private void checaCpfExiste(String cpf){
        if (operadorRepositorio.existePorCpf(cpf)) {
            throw new CpfAlreadyExistsException("CPF já cadastrado no sistema");
        }
    }

}