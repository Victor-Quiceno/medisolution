package com.quiceno.medisolution.entity;

import com.quiceno.medisolution.enums.EstadoCita;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteEntity paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoEntity medico;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCita estado;

}
