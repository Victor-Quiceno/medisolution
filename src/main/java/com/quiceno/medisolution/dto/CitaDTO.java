package com.quiceno.medisolution.dto;

import com.quiceno.medisolution.enums.EstadoCita;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CitaDTO {

    private Long id;
    private Long pacienteId;
    private PacienteDTO paciente;
    private Long medicoId;
    private MedicoDTO medico;
    private LocalDateTime fecha;
    private Long especialidadId;
    private EspecialidadDTO especialidad;
    private String motivo;
    private EstadoCita estado;

}
