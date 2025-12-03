package com.restaurante01.api_restaurante.usuarios.cliente.mapper;


import com.restaurante01.api_restaurante.usuarios.cliente.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.entity.Cliente;
import com.restaurante01.api_restaurante.usuarios.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {
    public Cliente mappearNovoCliente(CadastrarClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setSenha(dto.senha());
        cliente.setCpf(dto.cpf());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());
        cliente.setEstado(dto.estado());
        cliente.setCidade(dto.cidade());
        cliente.setBairro(dto.bairro());
        cliente.setRua(dto.rua());
        cliente.setCep(dto.cep());
        cliente.setNumeroResidencia(dto.numero());
        cliente.setComplemento(dto.complemento());
        cliente.setUserName(dto.userName());
        cliente.setRole(Role.USER);
        cliente.setPontuacaoFidelidade(0L);
        return cliente;
    }
    public ClienteDTO mapearClienteParaClienteDTO(Cliente novoCliente) {
        return new ClienteDTO(
                novoCliente.getId(),
                novoCliente.getNome(),
                novoCliente.getUsername(),
                novoCliente.getRole());
    }
    public List<ClienteDTO> mapearListaClienteParaClienteDTO(List<Cliente> clientes){
        return clientes.stream()
                .map(cliente -> mapearClienteParaClienteDTO(cliente)).toList();
    }
}
