package com.restaurante01.api_restaurante.modulos.operador.mapper;


import com.restaurante01.api_restaurante.modulos.operador.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.entity.Operador;
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
        operador.setRole(Role.USER);
        operador.setContaAtiva(true);
        return operador;
    }
    public OperadorDTO mapearOperadorParaOperadorDTO(Operador novoOperador) {
        return new OperadorDTO(
                novoOperador.getId(),
                novoOperador.getNome(),
                novoOperador.getUsername(),
                novoOperador.getRole());
    }
    public List<OperadorDTO> mapearListaClienteParaClienteDTO(List<Operador> clientes){
        return clientes.stream()
                .map(this::mapearOperadorParaOperadorDTO).toList();
    }
}
