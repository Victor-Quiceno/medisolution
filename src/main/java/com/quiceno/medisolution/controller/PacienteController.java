package com.quiceno.medisolution.controller;

import com.quiceno.medisolution.dto.PacienteDTO;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("{id}")
    public ResponseEntity<PacienteDTO> listarPorId (@PathVariable Long id){
        return ResponseEntity.ok(pacienteService.listarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<PacienteDTO>> listar (
            @RequestParam(required = false) Estado estado,
            @RequestParam(required = false) String numeroDocumento,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 10, sort = "apellido") Pageable pageable
            ){
        Page<PacienteDTO> pacientes = pacienteService.listar(estado, numeroDocumento, email, pageable);

        return ResponseEntity.ok(pacientes);
    }

    @PostMapping()
    public ResponseEntity<PacienteDTO> guardarPaciente(@Valid @RequestBody PacienteDTO dto) {

        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(@PathVariable Long id, @Valid @RequestBody PacienteDTO dto) {

        dto.setId(id);
        PacienteDTO pacienteActualizado = pacienteService.actualizarPaciente(dto);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteActualizado);
    }

    @DeleteMapping("/{id}")
    public boolean eliminarPaciente (@Valid @PathVariable Long id){
        pacienteService.eliminar(id);
        return true;
    }

}
