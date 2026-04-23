package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.validador;

import com.restaurante01.api_restaurante.compartilhado.utils.validadorcpf.ValidadorCpf;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
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

    public void validarNovoCliente(CadastrarClienteDTO dto, Boolean isUpdate) {

        ValidadorCpf.validarCpf(dto.cpf());
        if (!isUpdate) {
            checaEmailExiste(dto.email());
            checaCpfExiste(dto.cpf());
        }
    }
    public void validarAtualizacao(ClienteDTO dto, Cliente clienteExistente) {
        if (dto.cpf() != null && !dto.cpf().equals(clienteExistente.getUsername())) {
        }
    }

    private void checaEmailExiste(String email) {
        if (clienteRepositorio.existePorEmail(email)) {
            throw new EmailInvalidoExcecao("Email já cadastrado no sistema");
        }
    }


    private void checaCpfExiste(String cpf) {
        if (clienteRepositorio.existePorCpf(cpf)) {
            throw new CpfInvalidoExcecao("CPF já cadastrado no sistema");
        }
    }
}