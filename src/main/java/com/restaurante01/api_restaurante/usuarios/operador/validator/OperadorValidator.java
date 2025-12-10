package com.restaurante01.api_restaurante.usuarios.operador.validator;

import com.restaurante01.api_restaurante.core.utils.validadorcpf.ValidadorCpf;
import com.restaurante01.api_restaurante.usuarios.cliente.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.repository.ClienteRepository;
import com.restaurante01.api_restaurante.usuarios.exceptions.CpfAlreadyExistsException;
import com.restaurante01.api_restaurante.usuarios.exceptions.EmailAlreadyExistsException;
import com.restaurante01.api_restaurante.usuarios.exceptions.UsernameAlreadyExistsException;
import com.restaurante01.api_restaurante.usuarios.operador.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.usuarios.operador.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperadorValidator {

    @Autowired
    private OperadorRepository operadorRepository;
    public void validarNovoOperador(CadastrarOperadorDTO dto, Boolean isUpdate) {
        ValidadorCpf.validarCpf(dto.cpf());
        checaEmailExiste(dto.email());
        checaCpfExiste(dto.cpf());
        checaUserNameExiste(dto.userName());
        checaMatricula(dto.matricula());
    }
    private void checaMatricula(Long matricula){
        //adicionar regra caso necessario
    }
    private void checaEmailExiste(String email){
        if(operadorRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email ja cadastrado no sistema");
        }
    }
    private void checaCpfExiste(String cpf){
        if(operadorRepository.existsByCpf(cpf)){
            throw new CpfAlreadyExistsException("Cpf já cadastrado no sistema");
        }
    }
    private void checaUserNameExiste(String userName){
        if(operadorRepository.existsByUserName(userName)){
            throw new UsernameAlreadyExistsException(
                    "Já existe um userName com este Nome cadastrado no sistema");
        }
    }


}
