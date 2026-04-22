package com.restaurante01.api_restaurante.modulos.cupom.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CupomJPA extends JpaRepository<Cupom, Long> {
    Optional<Cupom> findByCodigoCupom(CodigoCupom codigoCupom);
}
