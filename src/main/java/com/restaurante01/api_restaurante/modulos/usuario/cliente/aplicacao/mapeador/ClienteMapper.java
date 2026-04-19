package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador;


import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.EnderecoCliente;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.role.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {

    //AJUSTAR PARA DTO RECEBER UM TIPO DE DADO DE ENDERECODTO DENTRO DELE
    public EnderecoCliente mapearEnderecoCliente(CadastrarClienteDTO dto){
        return new EnderecoCliente(dto.rua(), dto.numero(), dto.bairro(), dto.cidade(), dto.estado(), dto.cep(), dto.complemento(), dto.referencia());
    }

    public Cliente mappearNovoCliente(CadastrarClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setSenha(dto.senha());
        cliente.setCpf(dto.cpf());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());
        cliente.setEnderecoCliente(mapearEnderecoCliente(dto));
        cliente.setUserName(dto.userName());
        cliente.setRole(Role.USER);
        cliente.setContaAtiva(true);
        return cliente;
    }
    public ClienteDTO mapearClienteParaClienteDTO(Cliente novoCliente) {
        return new ClienteDTO(
                novoCliente.getId(),
                novoCliente.getNome(),
                novoCliente.getUsername(),
                novoCliente.getRole(),
                novoCliente.getPontuacaoFidelidade().getValor());
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
