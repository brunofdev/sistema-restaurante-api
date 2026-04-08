package com.restaurante01.api_restaurante.infraestrutura.autenticacao.service;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final OperadorRepositorio operadorRepositorio;
    private final ClienteRepositorio clienteRepositorio;

    public AuthorizationService(OperadorRepositorio operadorRepositorio, ClienteRepositorio clienteRepositorio) {
        this.operadorRepositorio = operadorRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var operador = operadorRepositorio.buscarPorUserName(userName);
        if (operador.isPresent()){
            return (UserDetails) operador.get();
        }

        var cliente = clienteRepositorio.buscarPorUserName(userName);
        if(cliente.isPresent()){
            return (UserDetails) cliente.get();
        }

        throw new UsernameNotFoundException("Nenhum usuário com o userName enviado foi localizado");
    }
}