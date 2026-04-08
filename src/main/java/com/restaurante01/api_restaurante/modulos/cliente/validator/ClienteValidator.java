package com.restaurante01.api_restaurante.modulos.cliente.validator;

import com.restaurante01.api_restaurante.compartilhado.validadorcpf.ValidadorCpf;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.repository.ClienteRepository;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.CpfAlreadyExistsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.EmailAlreadyExistsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.UsernameAlreadyExistsException;
import com.restaurante01.api_restaurante.modulos.operador.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidator {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private OperadorRepository operadorRepository;

    public void validarNovoCliente(CadastrarClienteDTO dto, Boolean isUpdate) {
        ValidadorCpf.validarCpf(dto.cpf());
        checaSeUsernameExisteEmOperadores(dto.userName());
        checaEmailExiste(dto.email());
        checaCpfExiste(dto.cpf());
        checaUserNameExiste(dto.userName());
    }
    private void checaEmailExiste(String email){
        if(clienteRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email ja cadastrado no sistema");
        }
    }
    private void checaSeUsernameExisteEmOperadores(String userName){
        if(operadorRepository.existsByUserName(userName)){
            throw  new UsernameAlreadyExistsException("Username já cadastrado por operador");
        }
    }
    private void checaCpfExiste(String cpf){
        if(clienteRepository.existsByCpf(cpf)){
            throw new CpfAlreadyExistsException("Cpf já cadastrado no sistema");
        }
    }
    private void checaUserNameExiste(String userName){
        if(clienteRepository.existsByUserName(userName)){
            throw new UsernameAlreadyExistsException(
                    "Já existe um userName com este Nome cadastrado no sistema");
        }
    }


}
