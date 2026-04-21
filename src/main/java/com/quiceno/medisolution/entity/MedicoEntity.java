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
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "medico_especialidad",
            joinColumns = @JoinColumn(name = "medico_id"), //Llave foránea de médico
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")) //Llave foránea de especialidad
    private Set<EspecialidadEntity> especialidades = new HashSet<>(); //Se usa set para evitar duplicados de especialidades y hacer más eficiente el N:M

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
