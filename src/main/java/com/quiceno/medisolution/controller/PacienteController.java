package com.quiceno.medisolution.controller;

import com.quiceno.medisolution.dto.PacienteDTO;
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

    @PostMapping()
    public ResponseEntity<PacienteDTO> guardarPaciente(@Valid @RequestBody PacienteDTO dto) {

        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteGuardado);
    }

    @GetMapping()
    public ResponseEntity<Page<PacienteDTO>> listarPacientesActivos(@PageableDefault(size = 10, sort = "nombre") Pageable pageable){
        return ResponseEntity.ok(pacienteService.listarActivo(pageable));
    }

    @GetMapping("/todo")
    public ResponseEntity<Page<PacienteDTO>> listarPacientes(@PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(pacienteService.listarTodo(pageable));
    }

    @GetMapping("/{numeroDocumento}")
    public ResponseEntity<PacienteDTO> buscarPorDocumento(@PathVariable String numeroDocumento) {

        PacienteDTO pacienteEncontrado = pacienteService.buscarPorNumeroDocumento(numeroDocumento);
        return ResponseEntity.ok(pacienteEncontrado);
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
