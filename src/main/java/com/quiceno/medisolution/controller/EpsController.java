package com.quiceno.medisolution.controller;

import com.quiceno.medisolution.dto.EpsDTO;
import com.quiceno.medisolution.service.EpsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API REST para la gestión de EPS.
 */
@RestController
@RequestMapping("/api/eps")
public class EpsController {

    private final EpsService epsService;

    public EpsController(EpsService epsService) {
        this.epsService = epsService;
    }

    @GetMapping
    public ResponseEntity<Page<EpsDTO>> listarActivas(@PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(epsService.listarActivas(pageable));
    }

    @GetMapping("/todo")
    public ResponseEntity<Page<EpsDTO>> listarTodo(@PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(epsService.listarTodas(pageable));
    }

    @GetMapping("/{nit}")
    public ResponseEntity<EpsDTO> listarPorNumeroDocumento(@Valid @PathVariable String nit) {
        EpsDTO epsEncontrada = epsService.buscarPorNit(nit);
        return ResponseEntity.status(HttpStatus.OK).body(epsEncontrada);
    }

    @PostMapping
    public ResponseEntity<EpsDTO> guardarEps(@Valid @RequestBody EpsDTO eps) {
        EpsDTO epsGuardada = epsService.guardar(eps);
        return ResponseEntity.status(HttpStatus.CREATED).body(epsGuardada);
    }

    @PutMapping("{id}")
    public ResponseEntity<EpsDTO> actualizarEps(@PathVariable Long id, @Valid @RequestBody EpsDTO eps) {
        eps.setId(id);
        EpsDTO epsActualizada = epsService.actualizar(eps);
        return ResponseEntity.status(HttpStatus.OK).body(epsActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEps(@Valid @PathVariable Long id) {
        epsService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
