package com.restaurante01.api_restaurante.modulos.cliente.aplicacao.mappeador;


import com.restaurante01.api_restaurante.modulos.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;
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
        cliente.setPontuacaoFidelidade(0);
        cliente.setContaAtiva(true);
        return cliente;
    }
    public ClienteDTO mapearClienteParaClienteDTO(Cliente novoCliente) {
        return new ClienteDTO(
                novoCliente.getId(),
                novoCliente.getNome(),
                novoCliente.getUsername(),
                novoCliente.getRole(),
                novoCliente.getPontuacaoFidelidade());
    }
    public List<ClienteDTO> mapearListaClienteParaClienteDTO(List<Cliente> clientes){
        return clientes.stream()
                .map(cliente -> mapearClienteParaClienteDTO(cliente)).toList();
    }
    public void atualizarEntidade(Cliente clienteExistente, ClienteDTO dto) {
        if (dto.nome() != null) {
            clienteExistente.setNome(dto.nome());
        }
        if (dto.userName() != null) {
            clienteExistente.setUserName(dto.userName());
        }
    }
}
