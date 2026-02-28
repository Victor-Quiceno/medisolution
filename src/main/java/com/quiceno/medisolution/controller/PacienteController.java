package com.quiceno.medisolution.controller;

import com.quiceno.medisolution.dto.PacienteDTO;
import com.quiceno.medisolution.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService){
        this.pacienteService = pacienteService;
    }

    @PostMapping()
    public ResponseEntity<PacienteDTO> guardarPaciente(@RequestBody PacienteDTO dto){

        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteGuardado); }

    @GetMapping()
    public ResponseEntity<List<PacienteDTO>> listarPacientes(){
        return ResponseEntity.ok(pacienteService.listar());
    }

    @GetMapping("/{numeroDocumento}")
    public ResponseEntity<PacienteDTO> buscarPorDocumento(@PathVariable String numeroDocumento){

        PacienteDTO pacienteEncontrado = pacienteService.buscarPorNumeroDocumento(numeroDocumento);
        return ResponseEntity.ok(pacienteEncontrado);
    }

}
