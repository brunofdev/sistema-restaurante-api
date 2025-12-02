package com.restaurante01.api_restaurante.usuarios.service;

import com.restaurante01.api_restaurante.usuarios.dto.entrada.CadastrarUsuarioDTO;
import com.restaurante01.api_restaurante.usuarios.dto.saida.UsuarioDTO;
import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
import com.restaurante01.api_restaurante.usuarios.enums.Role;
import com.restaurante01.api_restaurante.usuarios.exceptions.InvalidCredentialsException;
import com.restaurante01.api_restaurante.usuarios.exceptions.UserDontFoundException;
import com.restaurante01.api_restaurante.usuarios.mapper.UsuarioMapper;
import com.restaurante01.api_restaurante.usuarios.repository.UsuarioRepository;
import com.restaurante01.api_restaurante.usuarios.validator.UsuarioValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioValidator usuarioValidator;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper,
                          UsuarioValidator usuarioValidator, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.usuarioValidator = usuarioValidator;
        this.passwordEncoder = passwordEncoder;
    }
    public Usuario encontrarUsuarioPorCpf(String cpf){
        return usuarioRepository.findByCpf(cpf).orElseThrow(
                () -> new UserDontFoundException("Usuario não encontrado"));
    }
    public UsuarioDTO cadastrarNovoUsuario(CadastrarUsuarioDTO dto){
        usuarioValidator.validarNovoUsuario(dto, false);
        Usuario novoUsuario = usuarioRepository.save(usuarioMapper.mappearNovoUsuario(dto));
        return usuarioMapper.mapearUsuarioParaUsuarioDTO(novoUsuario);
    }
    public UsuarioDTO cadastrarNovoUsuarioAdmin1(CadastrarUsuarioDTO dto){
        usuarioValidator.validarNovoUsuario(dto, false);
        Usuario novoUsuario = usuarioMapper.mappearNovoUsuario(dto);
        novoUsuario.setRole(Role.ADMIN1);
        usuarioRepository.save(novoUsuario);
        return usuarioMapper.mapearUsuarioParaUsuarioDTO(novoUsuario);
    }
    public UsuarioDTO cadastrarNovoUsuarioAdmin3(CadastrarUsuarioDTO dto){
        usuarioValidator.validarNovoUsuario(dto, false);
        Usuario novoUsuario = usuarioMapper.mappearNovoUsuario(dto);
        novoUsuario.setRole(Role.ADMIN3);
        usuarioRepository.save(novoUsuario);
        return usuarioMapper.mapearUsuarioParaUsuarioDTO(novoUsuario);
    }
    public UsuarioDTO autenticarUsuario(String cpf, String senha){
        Usuario usuario = encontrarUsuarioPorCpf(cpf);
        if (!passwordEncoder.matches(senha, usuario.getSenha())){
            throw new InvalidCredentialsException("Credenciais invalidas");
        };
        return usuarioMapper.mapearUsuarioParaUsuarioDTO(usuario);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioMapper.mapearListaUsuarioParaUsuarioDTO(usuarioRepository.findAll());
    }
}
