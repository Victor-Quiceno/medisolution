package com.quiceno.medisolution.controller;

import com.quiceno.medisolution.dto.MedicoDTO;
import com.quiceno.medisolution.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    public ResponseEntity<Page<MedicoDTO>> listarActivos(@PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(medicoService.listarActivo(pageable));
    }

    @GetMapping("/todo")
    public ResponseEntity<Page<MedicoDTO>> listarTodo(@PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(medicoService.listarTodo(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.listarPorId(id));
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<MedicoDTO> listarPorDocumento(@PathVariable String documento) {
        return ResponseEntity.ok(medicoService.listarPorNumeroDocumento(documento));
    }

    @PostMapping
    public ResponseEntity<MedicoDTO> guardar(@Valid @RequestBody MedicoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody MedicoDTO dto) {
        dto.setId(id);
        MedicoDTO medicoActualizado = medicoService.actualizar(dto);
        return ResponseEntity.ok(medicoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        medicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}