package com.restaurante01.api_restaurante.usuarios.cliente;

import com.restaurante01.api_restaurante.core.utils.validadorcpf.ValidadorCpf;
import com.restaurante01.api_restaurante.usuarios.cliente.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.repository.ClienteRepository;
import com.restaurante01.api_restaurante.usuarios.exceptions.CpfAlreadyExistsException;
import com.restaurante01.api_restaurante.usuarios.exceptions.EmailAlreadyExistsException;
import com.restaurante01.api_restaurante.usuarios.exceptions.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidator {

    @Autowired
    private ClienteRepository usuarioRepository;
    public void validarNovoCliente(CadastrarClienteDTO dto, Boolean isUpdate) {
        ValidadorCpf.validarCpf(dto.cpf());
        checaEmailExiste(dto.email());
        checaCpfExiste(dto.cpf());
        checaUserNameExiste(dto.userName());
    }
    private void checaEmailExiste(String email){
        if(usuarioRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email ja cadastrado no sistema");
        }
    }
    private void checaCpfExiste(String cpf){
        if(usuarioRepository.existsByCpf(cpf)){
            throw new CpfAlreadyExistsException("Cpf já cadastrado no sistema");
        }
    }
    private void checaUserNameExiste(String userName){
        if(usuarioRepository.existsByUserName(userName)){
            throw new UsernameAlreadyExistsException(
                    "Já existe um userName com este Nome cadastrado no sistema");
        }
    }


}
