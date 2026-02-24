package com.quiceno.medisolution.controller;

import com.quiceno.medisolution.dto.PacienteDTO;
import com.quiceno.medisolution.service.PacienteService;
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
    public PacienteDTO guardarPaciente(@RequestBody PacienteDTO dto){return pacienteService.guardarPaciente(dto);}

    @GetMapping()
    public List<PacienteDTO> listarPacientes(){
        return pacienteService.listar();
    }

    @GetMapping("/{numeroDocumento}")
    public PacienteDTO buscarPorDocumento(@PathVariable String numeroDocumento){
        return pacienteService.buscarPorNumeroDocumento(numeroDocumento);
    }

}
