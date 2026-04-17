package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mappeador;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.EnderecoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {
    public Endereco paraEndereco (EnderecoDTO dto){
        if(dto != null) {
            return new Endereco(
                    dto.rua(),
                    dto.numero(),
                    dto.bairro(),
                    dto.cidade(),
                    dto.estado(),
                    dto.cep(),
                    dto.referencia()
            );
        }
        return null;
    }
}
