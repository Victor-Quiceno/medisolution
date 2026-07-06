package com.quiceno.medisolution.controller;

import com.quiceno.medisolution.dto.CitaDTO;
import com.quiceno.medisolution.enums.EstadoCita;
import com.quiceno.medisolution.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService){
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<Page<CitaDTO>> listar (
            @RequestParam(required = false)EstadoCita estado,
            @RequestParam(required = false) String documentoPaciente,
            @RequestParam(required = false) String tarjetaMedico,
            @PageableDefault(size = 10, sort = "fecha") Pageable pageable){

        Page<CitaDTO> citas = citaService.listarCitasDinamico(estado, documentoPaciente, tarjetaMedico, pageable);

        return ResponseEntity.ok(citas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> listarPorId (@PathVariable Long id){
        return ResponseEntity.ok(citaService.listarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CitaDTO> guardar (@Valid @RequestBody CitaDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizar (@Valid @RequestBody CitaDTO dto, @PathVariable Long id){
        dto.setId(id);
        return ResponseEntity.ok(citaService.actualizar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
