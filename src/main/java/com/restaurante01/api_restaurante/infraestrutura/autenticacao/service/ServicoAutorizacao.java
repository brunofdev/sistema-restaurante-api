package com.restaurante01.api_restaurante.infraestrutura.autenticacao.service;

import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.CredenciaisDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.ClienteLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.OperadorLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.AutenticarClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso.AutenticarOperadorCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
            return clienteRepositorio.buscarPorCpf(cpf)
                    .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado."));
        }

        // Se não for USER, é ADMIN/Operador
        return operadorRepositorio.buscarPorCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Operador não encontrado."));
    }
}