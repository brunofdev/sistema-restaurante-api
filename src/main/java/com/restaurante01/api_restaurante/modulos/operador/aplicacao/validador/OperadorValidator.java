package com.restaurante01.api_restaurante.modulos.operador.aplicacao.validador;

import com.restaurante01.api_restaurante.compartilhado.validadorcpf.ValidadorCpf;
import com.restaurante01.api_restaurante.modulos.cliente.infraestrutura.persistencia.ClienteJPA;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.CpfAlreadyExistsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.EmailAlreadyExistsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.UsernameAlreadyExistsException;
import com.restaurante01.api_restaurante.modulos.operador.api.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.infraestrutura.persistencia.OperadorJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperadorValidator {

    @Autowired
    private OperadorJPA operadorJPA;
    @Autowired
    private ClienteJPA clienteJPA;

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
        if(operadorJPA.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email ja cadastrado no sistema");
        }
    }
    private void checaUserNameExisteEmCliente(String userName){
        if(clienteJPA.existsByUserName(userName)){
            throw new UsernameAlreadyExistsException("Já existe Operador com userName cadastrado");
        }
    }
    private void checaCpfExiste(String cpf){
        if(operadorJPA.existsByCpf(cpf)){
            throw new CpfAlreadyExistsException("Cpf já cadastrado no sistema");
        }
    }
    private void checaUserNameExiste(String userName){
        if(operadorJPA.existsByUserName(userName)){
            throw new UsernameAlreadyExistsException(
                    "Já existe um userName com este Nome cadastrado no sistema");
        }
    }


}
