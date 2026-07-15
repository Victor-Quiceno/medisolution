package com.quiceno.medisolution.dto;

import com.quiceno.medisolution.enums.EstadoCita;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CitaDTO {

    private Long id;

    @NotNull(message = "El paciente es obligatorio")
    private Long pacienteId;
    
    private PacienteDTO paciente;

    @NotNull(message = "El médico es obligatorio")
    private Long medicoId;
    
    private MedicoDTO medico;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha debe ser actual o en el futuro")
    private LocalDateTime fecha;

    @NotNull(message = "La especialidad es obligatoria")
    private Long especialidadId;
    
    private EspecialidadDTO especialidad;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;
    
    private EstadoCita estado;

}
