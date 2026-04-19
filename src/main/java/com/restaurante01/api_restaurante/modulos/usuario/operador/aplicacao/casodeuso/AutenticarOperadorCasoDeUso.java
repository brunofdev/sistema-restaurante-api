package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.InvalidCredentialsException;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.mapeador.OperadorMapper;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticarOperadorCasoDeUso {

    private final ObterOperadorPorCpfCasoDeUso obterOperadorPorCpf;
    private final OperadorMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public AutenticarOperadorCasoDeUso(ObterOperadorPorCpfCasoDeUso obterOperadorPorCpf, OperadorMapper mapper, PasswordEncoder passwordEncoder) {
        this.obterOperadorPorCpf = obterOperadorPorCpf;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public OperadorDTO executar(String cpf, String senha) {
        Operador operador = obterOperadorPorCpf.executar(cpf);

        if (!passwordEncoder.matches(senha, operador.getSenha())) {
            throw new InvalidCredentialsException("Credenciais inválidas");
        }

        return mapper.mapearOperadorParaOperadorDTO(operador);
    }
}