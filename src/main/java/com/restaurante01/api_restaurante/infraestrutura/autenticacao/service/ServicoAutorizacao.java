package com.restaurante01.api_restaurante.infraestrutura.autenticacao.service;


import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServicoAutorizacao {

    private final OperadorRepositorio operadorRepositorio;
    private final ClienteRepositorio clienteRepositorio;

    public ServicoAutorizacao(OperadorRepositorio operadorRepositorio,
                              ClienteRepositorio clienteRepositorio) {
        this.operadorRepositorio = operadorRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    public UserDetails carregarUsuarioPorCpfERole(String cpf, String role) {
        if (role.equals("USER")) { // Supondo que a role do Cliente seja USER
            return clienteRepositorio.buscarPorCpf(new Cpf(cpf))
                    .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado."));
        }

        // Se não for USER, é ADMIN/Operador
        return operadorRepositorio.buscarPorCpf(new Cpf(cpf))
                .orElseThrow(() -> new UsernameNotFoundException("Operador não encontrado."));
    }
}