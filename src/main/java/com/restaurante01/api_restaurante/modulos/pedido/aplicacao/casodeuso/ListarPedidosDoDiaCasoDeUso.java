package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ListarPedidosDoDiaCasoDeUso {

    private final PedidoRepositorio repository;
    private final PedidoMapeador mapper;

    public ListarPedidosDoDiaCasoDeUso(PedidoRepositorio repository, PedidoMapeador mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<PedidoCriadoDTO> executar(Pageable pageable) {
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicio = hoje.atStartOfDay();
        LocalDateTime fim = hoje.atTime(23, 59, 59, 999999999);

        return repository.buscarPorDataCriacaoEntre(inicio, fim, pageable)
                .map(mapper::mapearPedidoCriadoDto);
    }
}