package com.restaurante01.api_restaurante.modulos.cliente.aplicacao.validador;

import com.restaurante01.api_restaurante.compartilhado.utils.validadorcpf.ValidadorCpf;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.CpfAlreadyExistsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.EmailAlreadyExistsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.UsernameAlreadyExistsException;
import com.restaurante01.api_restaurante.modulos.operador.dominio.repositorio.OperadorRepositorio; // Idealmente a interface de domínio
import org.springframework.stereotype.Component;

@Component
public class ClienteValidator {

    private final ClienteRepositorio clienteRepositorio;
    private final OperadorRepositorio operadorRepository;

    public ClienteValidator(ClienteRepositorio clienteRepositorio, OperadorRepositorio operadorRepository) {
        this.clienteRepositorio = clienteRepositorio;
        this.operadorRepository = operadorRepository;
    }

    public void validarNovoCliente(CadastrarClienteDTO dto, Boolean isUpdate) {

        ValidadorCpf.validarCpf(dto.cpf());


        if (!isUpdate) {
            checaEmailExiste(dto.email());
            checaCpfExiste(dto.cpf());
            checaUserNameExiste(dto.userName());
            checaSeUsernameExisteEmOperadores(dto.userName());
        }
    }
    public void validarAtualizacao(ClienteDTO dto, Cliente clienteExistente) {
        if (dto.userName() != null && !dto.userName().equals(clienteExistente.getUsername())) {
            checaUserNameExiste(dto.userName());
            checaSeUsernameExisteEmOperadores(dto.userName());
        }
    }

    private void checaEmailExiste(String email) {
        if (clienteRepositorio.existePorEmail(email)) {
            throw new EmailAlreadyExistsException("Email já cadastrado no sistema");
        }
    }

    private void checaSeUsernameExisteEmOperadores(String userName) {
        if (operadorRepository.existePorUserName(userName)) {
            throw new UsernameAlreadyExistsException("Username já cadastrado por um operador");
        }
    }

    private void checaCpfExiste(String cpf) {
        if (clienteRepositorio.existePorCpf(cpf)) {
            throw new CpfAlreadyExistsException("CPF já cadastrado no sistema");
        }
    }

    private void checaUserNameExiste(String userName) {
        if (clienteRepositorio.existePorUserName(userName)) {
            throw new UsernameAlreadyExistsException(
                    "Já existe um userName com este nome cadastrado no sistema");
        }
    }
}