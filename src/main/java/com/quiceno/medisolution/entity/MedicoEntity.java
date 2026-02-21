package com.quiceno.medisolution.entity;

import com.quiceno.medisolution.enums.Areas;
import com.quiceno.medisolution.enums.Estado;
import com.quiceno.medisolution.enums.Genero;
import com.quiceno.medisolution.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;

    @Column(name = "tarjeta_profesional", nullable = false, unique = true, length = 50)
    private String tarjetaProfesional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id", nullable = false) //Aquí había puesto que sea único, lo quité por si acaso
    private EspecialidadEntity especialidad;

    @Column(name = "genero", nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(name = "email")
    private String email;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    //¿Esto podría ser una entidad en vez de un ENUM?
    @Column(name = "area", nullable = false)
    @Enumerated(EnumType.STRING)
    private Areas area;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;

}
