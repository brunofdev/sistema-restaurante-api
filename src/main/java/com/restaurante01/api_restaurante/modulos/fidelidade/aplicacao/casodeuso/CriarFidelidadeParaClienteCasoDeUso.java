package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.porta.FidelidadeClientePorta;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.repositorio.FidelidadeRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CriarFidelidadeParaClienteCasoDeUso {

    private final FidelidadeRepositorio repositorio;
    private final FidelidadeClientePorta atualizarReferenciaCliente;

    @Transactional
    public void executar(Long clienteId) {
        Fidelidade fidelidade = Fidelidade.criar(clienteId);
        Fidelidade fidelidadeSalva = repositorio.salvar(fidelidade);
        atualizarReferenciaCliente.atualizarReferencia(clienteId, fidelidadeSalva.getId());
    }
}
