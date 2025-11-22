package com.restaurante01.api_restaurante.usuarios.mapper;

import com.restaurante01.api_restaurante.usuarios.dto.entrada.CadastrarUsuarioDTO;
import com.restaurante01.api_restaurante.usuarios.dto.saida.UsuarioDTO;
import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
import com.restaurante01.api_restaurante.usuarios.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {
    public Usuario mappearNovoUsuario(CadastrarUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setSenha(dto.senha());
        usuario.setCpf(dto.cpf());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setEstado(dto.estado());
        usuario.setCidade(dto.cidade());
        usuario.setRua(dto.rua());
        usuario.setCep(dto.cep());
        usuario.setNumeroResidencia(dto.numero());
        usuario.setComplemento(dto.complemento());
        usuario.setUserName(dto.userName());
        usuario.setRole(UserRole.USER);
        return usuario;
    }
    public UsuarioDTO mapearUsuarioParaUsuarioDTO(Usuario novoUsuario) {
        return new UsuarioDTO(
                novoUsuario.getId(),
                novoUsuario.getNome(),
                novoUsuario.getUsername(),
                novoUsuario.getRole());
    }
    public List<UsuarioDTO> mapearListaUsuarioParaUsuarioDTO(List<Usuario> usuarios){
        return usuarios.stream()
                .map(usuario -> mapearUsuarioParaUsuarioDTO(usuario)).toList();
    }
}
