package com.restaurante01.api_restaurante.infraestrutura.autenticacao.service;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.repositorio.ClienteRepository;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.UserNotFoundException;
import com.restaurante01.api_restaurante.modulos.operador.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private OperadorRepository operadorRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UserNotFoundException {
        var operador = operadorRepository.findByUserName(userName);
        if (operador.isPresent()){
            return operador.get();
        }
        var cliente = clienteRepository.findByUserName(userName);
        if(cliente.isPresent()){
            return cliente.get();
        }
        throw new UserNotFoundException("Nenhum usuario enviado no token foi localizado");
    }
}
