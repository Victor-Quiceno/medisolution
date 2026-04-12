package com.quiceno.medisolution.controller;

import com.quiceno.medisolution.dto.EpsDTO;
import com.quiceno.medisolution.service.EpsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eps")
public class EpsController {

    private final EpsService epsService;
    public EpsController(EpsService epsService) {this.epsService = epsService;}

    @GetMapping
    public ResponseEntity<List<EpsDTO>> listarActivas (){
        return ResponseEntity.ok(epsService.listarActivas());
    }

    @GetMapping("/todo")
    public ResponseEntity<List<EpsDTO>> listarTodo (){
        return ResponseEntity.ok(epsService.listarTodas());
    }

    @GetMapping("/{nit}")
    public ResponseEntity<EpsDTO> listarPorNumeroDocumento(@Valid @PathVariable String nit){
        EpsDTO epsEncontrada = epsService.buscarPorNit(nit);
        return ResponseEntity.status(HttpStatus.OK).body(epsEncontrada);
    }


    @PostMapping
    public ResponseEntity<EpsDTO> guardarEps(@Valid @RequestBody EpsDTO eps){
        EpsDTO epsGuardada = epsService.guardar(eps);
        return ResponseEntity.status(HttpStatus.CREATED).body(epsGuardada);
    }

    @PutMapping
    public ResponseEntity<EpsDTO> actualizarEps(@Valid @RequestBody EpsDTO eps){
        EpsDTO epsActualizada = epsService.actualizar(eps);
        return ResponseEntity.status(HttpStatus.OK).body(epsActualizada);
    }

    @DeleteMapping("/{id}")
    public boolean eliminarEps(@Valid @PathVariable Long id){
        epsService.eliminar(id);
        return true;
    }
}
