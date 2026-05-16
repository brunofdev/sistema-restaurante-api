package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.GatilhoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador.ClienteMapeador;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.payload.ClienteCriadoPayload;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.validador.ClienteValidador;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.evento.ClienteCriadoEvento;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarClienteCasoDeUso {

    private final ClienteRepositorio repository;
    private final ClienteValidador validator;
    private final ClienteMapeador mapper;
    private final ApplicationEventPublisher publicadorDeEvento;
    private final OutboxRepositorio outboxRepositorio;
    private final ObjectMapper objectMapper;

    public CadastrarClienteCasoDeUso(ClienteRepositorio repository, ClienteValidador validator, ClienteMapeador mapper,
                                     ApplicationEventPublisher publicadorDeEvento, OutboxRepositorio outboxRepositorio,
                                     ObjectMapper objectMapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.publicadorDeEvento = publicadorDeEvento;
        this.outboxRepositorio = outboxRepositorio;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public ClienteDTO executar(CadastrarClienteDTO dto) {
        validator.validarNovoCliente(dto, false);
        Cliente cliente = mapper.mappearNovoCliente(dto);
        Cliente clienteSalvo = repository.salvar(cliente);
        publicarEvento(clienteSalvo.getId());
        return mapper.mapearClienteParaClienteDTO(clienteSalvo);
    }

    private void publicarEvento(Long clienteId) {
        try {
            ClienteCriadoPayload payload = new ClienteCriadoPayload(clienteId);
            outboxRepositorio.salvar(OutboxEvento.criar(
                    Agregado.CLIENTE, clienteId,
                    GatilhoEvento.CLIENTE_CADASTRADO,
                    TipoEvento.CRIAR_FIDELIDADE_CLIENTE,
                    objectMapper.writeValueAsString(payload)));
            publicadorDeEvento.publishEvent(new ClienteCriadoEvento(clienteId));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao publicar evento ClienteCriado para clienteId=" + clienteId, e);
        }
    }
}
