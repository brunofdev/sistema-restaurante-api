package com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.mapeador;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperadorMapeador {
    public Email mapearEmail (String email){
        return new Email(email);
    }
    public Cpf mapearCpf (String cpf){
        return new Cpf(cpf);
    }


    public Operador mappearNovoOperador(CadastrarOperadorDTO dto) {
        return Operador.criar(
                dto.nome(),
                dto.senha(),
                mapearEmail(dto.email()),
                mapearCpf(dto.cpf())
        );
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
    }
}