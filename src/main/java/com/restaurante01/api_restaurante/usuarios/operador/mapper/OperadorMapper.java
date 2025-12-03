package com.restaurante01.api_restaurante.usuarios.operador.mapper;


import com.restaurante01.api_restaurante.usuarios.operador.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.usuarios.operador.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.usuarios.operador.entity.Operador;
import com.restaurante01.api_restaurante.usuarios.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperadorMapper {
    public Operador mappearNovoCliente(CadastrarOperadorDTO dto) {
        Operador operador = new Operador();
        operador.setNome(dto.nome());
        operador.setSenha(dto.senha());
        operador.setCpf(dto.cpf());
        operador.setTelefone(dto.telefone());
        operador.setEmail(dto.email());
        operador.setEstado(dto.estado());
        operador.setCidade(dto.cidade());
        operador.setBairro(dto.bairro());
        operador.setRua(dto.rua());
        operador.setCep(dto.cep());
        operador.setNumeroResidencia(dto.numero());
        operador.setComplemento(dto.complemento());
        operador.setUserName(dto.userName());
        operador.setRole(Role.USER);
        return operador;
    }
    public OperadorDTO mapearClienteParaClienteDTO(Operador novoCliente) {
        return new OperadorDTO(
                novoCliente.getId(),
                novoCliente.getNome(),
                novoCliente.getUsername(),
                novoCliente.getRole());
    }
    public List<OperadorDTO> mapearListaClienteParaClienteDTO(List<Operador> clientes){
        return clientes.stream()
                .map(operador -> mapearClienteParaClienteDTO(operador)).toList();
    }
}
