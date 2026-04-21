package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import com.restaurante01.api_restaurante.infraestrutura.security.auditoria.Auditable;


import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Cupom extends Auditable {

    private Long id;
    private String codigo; //transformar em um Value Object
    private RegraValorDesconto regraValorDesconto;
    private Integer quantidade;
    private BigDecimal valorMinimoAplicavel;
    private BigDecimal valorMaximoAplicavel;
    private LocalDateTime dataDeInicio;
    private LocalDateTime dateDeTermino;
    private boolean valido;

}
