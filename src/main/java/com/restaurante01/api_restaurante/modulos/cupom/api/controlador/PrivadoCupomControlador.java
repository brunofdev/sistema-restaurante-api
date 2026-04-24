package com.restaurante01.api_restaurante.modulos.cupom.api.controlador;

import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.ListarTodosCuponsCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.role.Role;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//FALTA AJUSTAR E CRIAR ENDPOINTS AINDA PARA CRUD
@RestController
@RequestMapping("/cupom")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "9. Cupom - Administradores", description = "Ações exclusivas da criação de cupons")
@SecurityRequirement(name = "bearerAuth")
public class PrivadoCupomControlador {

    private final ListarTodosCuponsCasoDeUso listarTodosCuponsCasoDeUso;

    public PrivadoCupomControlador(ListarTodosCuponsCasoDeUso listarTodosCuponsCasoDeUso) {
        this.listarTodosCuponsCasoDeUso = listarTodosCuponsCasoDeUso;
    }

    @GetMapping
    public ResponseEntity<List<Cupom>> listarTodosCuponsExistente(){
        return ResponseEntity.ok(listarTodosCuponsCasoDeUso.executar());
    }
}
