package com.restaurante01.api_restaurante.modulos.operador.validator;

import com.restaurante01.api_restaurante.compartilhado.validadorcpf.ValidadorCpf;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.repositorio.ClienteRepository;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.CpfAlreadyExistsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.EmailAlreadyExistsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.UsernameAlreadyExistsException;
import com.restaurante01.api_restaurante.modulos.operador.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperadorValidator {

    @Autowired
    private OperadorRepository operadorRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public void validarNovoOperador(CadastrarOperadorDTO dto, Boolean isUpdate) {
        ValidadorCpf.validarCpf(dto.cpf());
        checaUserNameExisteEmCliente(dto.userName());
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
    private void checaUserNameExisteEmCliente(String userName){
        if(clienteRepository.existsByUserName(userName)){
            throw new UsernameAlreadyExistsException("Já existe Operador com userName cadastrado");
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
