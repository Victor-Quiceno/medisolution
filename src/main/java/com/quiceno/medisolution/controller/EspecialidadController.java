package com.quiceno.medisolution.controller;

import com.quiceno.medisolution.dto.EspecialidadDTO;
import com.quiceno.medisolution.service.EspecialidadService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;
    public EspecialidadController (EspecialidadService especialidadService){
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public ResponseEntity<Page<EspecialidadDTO>> listarActivas (@PageableDefault(size = 10, sort = "nombre") Pageable pageable){

        return ResponseEntity.ok(especialidadService.listarActivas(pageable));
    }

    @GetMapping("/todo")
    public ResponseEntity<Page<EspecialidadDTO>> listarTodo (@PageableDefault(size = 10, sort = "nombre") Pageable pageable){
        return ResponseEntity.ok(especialidadService.listarTodo(pageable));
    }

    @PostMapping
    public ResponseEntity <EspecialidadDTO> guardar (@Valid @RequestBody EspecialidadDTO dto){
        EspecialidadDTO guardado = especialidadService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> actualizar (@PathVariable Long id, @Valid @RequestBody EspecialidadDTO dto){
        dto.setId(id);
        EspecialidadDTO actualizado = especialidadService.actualizar(dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        especialidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
