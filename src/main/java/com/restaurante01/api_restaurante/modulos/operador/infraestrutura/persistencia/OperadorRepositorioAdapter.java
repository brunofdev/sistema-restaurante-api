package com.restaurante01.api_restaurante.modulos.operador.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class OperadorRepositorioAdapter implements OperadorRepositorio {
    private final OperadorJPA jpa;

    public OperadorRepositorioAdapter(OperadorJPA jpa) {
        this.jpa = jpa;
    }

    @Override public Operador salvar(Operador operador) { return jpa.save(operador); }
    @Override public Optional<Operador> buscarPorId(Long id) { return jpa.findById(id); }
    @Override public Optional<Operador> buscarPorCpf(String cpf) { return jpa.findByCpf(cpf); }
    @Override public Optional<Operador> buscarPorUserName(String userName) { return jpa.findByUserName(userName); }
    @Override public List<Operador> buscarTodos() { return jpa.findAll(); }
    @Override public boolean existePorCpf(String cpf) { return jpa.existsByCpf(cpf); }
    @Override public boolean existePorEmail(String email) { return jpa.existsByEmail(email); }
    @Override public boolean existePorUserName(String userName) { return jpa.existsByUserName(userName); }
}