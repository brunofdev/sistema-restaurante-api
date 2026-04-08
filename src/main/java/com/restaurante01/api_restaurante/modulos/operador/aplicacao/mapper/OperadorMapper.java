package com.restaurante01.api_restaurante.modulos.operador.aplicacao.mapper;

import com.restaurante01.api_restaurante.modulos.operador.api.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperadorMapper {

    public Operador mappearNovoOperador(CadastrarOperadorDTO dto) {
        Operador operador = new Operador();
        operador.setMatricula(dto.matricula());
        operador.setNome(dto.nome());
        operador.setSenha(dto.senha());
        operador.setCpf(dto.cpf());
        operador.setEmail(dto.email());
        operador.setUserName(dto.userName());
        operador.setRole(Role.ADMIN1);
        operador.setContaAtiva(true);
        return operador;
    }

    public OperadorDTO mapearOperadorParaOperadorDTO(Operador operador) {
        return new OperadorDTO(
                operador.getId(),
                operador.getNome(),
                operador.getUsername(),
                operador.getRole());
    }

    public List<OperadorDTO> mapearListaOperadorParaOperadorDTO(List<Operador> operadores) {
        return operadores.stream()
                .map(this::mapearOperadorParaOperadorDTO).toList();
    }

    public void atualizarEntidade(Operador operadorExistente, OperadorDTO dto) {
        if (dto.nome() != null) {
            operadorExistente.setNome(dto.nome());
        }
        if (dto.userName() != null) {
            operadorExistente.setUserName(dto.userName());
        }
    }
}