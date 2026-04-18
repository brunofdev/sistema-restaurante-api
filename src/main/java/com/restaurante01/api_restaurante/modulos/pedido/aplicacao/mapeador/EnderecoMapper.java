package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.EnderecoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.Endereco;
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
