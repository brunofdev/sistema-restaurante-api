package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador;


import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.EnderecoCliente;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class ClienteMapeador {

    //AJUSTAR PARA DTO RECEBER UM TIPO DE DADO DE ENDERECODTO DENTRO DELE
    public EnderecoCliente mapearEnderecoCliente(CadastrarClienteDTO dto){
        return new EnderecoCliente(dto.rua(), dto.numero(), dto.bairro(), dto.cidade(), dto.estado(), dto.cep(), dto.complemento(), dto.referencia());
    }
    public Email mapearEmail (String email){
        return new Email(email);
    }
    public Cpf mapearCpf (String cpf){
        return new Cpf(cpf);
    }

    public Cliente mappearNovoCliente(CadastrarClienteDTO dto) {
       return  Cliente.criar(
                dto.nome(),
                dto.senha(),
                mapearEmail(dto.email()),
                mapearCpf(dto.cpf()),
                mapearEnderecoCliente(dto),
                dto.telefone()
        );
    }
    public ClienteDTO mapearClienteParaClienteDTO(Cliente novoCliente, Integer pontuacaoFidelidade) {
        return new ClienteDTO(
                novoCliente.getId(),
                novoCliente.getNome(),
                novoCliente.getUsername(),
                novoCliente.getRole(),
                (pontuacaoFidelidade == null) ? 0 : pontuacaoFidelidade);
    }
    public Page<ClienteDTO> mapearListaClienteParaClienteDTO(
            Page<Cliente> clientes,
            Map<Long, Integer> pontuacoes) {
        return clientes.map(cliente ->
                mapearClienteParaClienteDTO(
                        cliente,
                        pontuacoes.getOrDefault(cliente.getId(), 0) //default valor de teste
                )
        );
    }
    public void atualizarEntidade(Cliente clienteExistente, ClienteDTO dto) {
        if (dto.nome() != null) {
            clienteExistente.setNome(dto.nome());
        }
    }
}
