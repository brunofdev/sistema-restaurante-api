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
    private final ClienteRepositorio clienteRepositorio;

    public OperadorValidator(OperadorRepositorio operadorRepositorio, ClienteRepositorio clienteRepositorio) {
        this.operadorRepositorio = operadorRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    public void validarNovoOperador(CadastrarOperadorDTO dto, Boolean isUpdate) {
        ValidadorCpf.validarCpf(dto.cpf());

        if (!isUpdate) {
            checaEmailExiste(dto.email());
            checaCpfExiste(dto.cpf());
            checaUserNameExiste(dto.userName());
            checaUserNameExisteEmCliente(dto.userName());
            checaMatricula(dto.matricula());
        }
    }

    public void validarAtualizacao(OperadorDTO dto, Operador operadorExistente) {
        if (dto.userName() != null && !dto.userName().equals(operadorExistente.getUsername())) {
            checaUserNameExiste(dto.userName());
            checaUserNameExisteEmCliente(dto.userName());
        }
    }

    private void checaMatricula(Long matricula){
    }

    private void checaEmailExiste(String email){
        if (operadorRepositorio.existePorEmail(email)) {
            throw new EmailAlreadyExistsException("Email já cadastrado no sistema");
        }
    }

    private void checaUserNameExisteEmCliente(String userName){
        if (clienteRepositorio.existePorUserName(userName)) {
            throw new UsernameAlreadyExistsException("Já existe um Cliente com este userName cadastrado");
        }
    }

    private void checaCpfExiste(String cpf){
        if (operadorRepositorio.existePorCpf(cpf)) {
            throw new CpfAlreadyExistsException("CPF já cadastrado no sistema");
        }
    }

    private void checaUserNameExiste(String userName){
        if (operadorRepositorio.existePorUserName(userName)) {
            throw new UsernameAlreadyExistsException("Já existe um Operador com este userName cadastrado no sistema");
        }
    }
}