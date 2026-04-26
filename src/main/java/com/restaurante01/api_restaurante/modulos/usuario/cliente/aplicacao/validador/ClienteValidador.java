package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.validador;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.CpfInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.EmailInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidador {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteValidador(ClienteRepositorio clienteRepositorio, OperadorRepositorio operadorRepository) {
        this.clienteRepositorio = clienteRepositorio;
    }

    //refatorar para remover isUpdate
    public void validarNovoCliente(CadastrarClienteDTO dto, Boolean isUpdate) {
        Cpf cpf = new Cpf(dto.cpf());
        Email email = new Email(dto.email());
        if (!isUpdate) {
            checaEmailExiste(email);
            checaCpfExiste(cpf);
        }
    }
    public void validarAtualizacao(ClienteDTO dto, Cliente clienteExistente) {
        if (dto.cpf() != null) {
            clienteExistente.getUsername();
        }
    }

    private void checaEmailExiste(Email email) {
        if (clienteRepositorio.existePorEmail(email)) {
            throw new EmailInvalidoExcecao("Email já cadastrado no sistema");
        }
    }


    private void checaCpfExiste(Cpf cpf) {
        if (clienteRepositorio.existePorCpf(cpf)) {
            throw new CpfInvalidoExcecao("CPF já cadastrado no sistema");
        }
    }
}