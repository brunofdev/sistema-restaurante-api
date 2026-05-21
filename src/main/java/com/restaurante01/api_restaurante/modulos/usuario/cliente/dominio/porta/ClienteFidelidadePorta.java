package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.porta;

import java.util.List;
import java.util.Map;

public interface ClienteFidelidadePorta {
    int obterPontuacaoFidelidade(Long idCliente);
    Map<Long, Integer> obterListaDeFidelidade(List<Long> idsCliente);
}
