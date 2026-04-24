package com.grupo2.EduPerformance.EduPerformance.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

// Entidad Perfil con detalles adicionales del Usuario.
@Table(name = "perfiles")
public class Perfil {
    // Identificador único del perfil.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dirección de residencia.
    private String direccion;

    // Número de teléfono de contacto.
    private String telefono;

    // Relación inversa Uno a Uno con Usuario.
    @OneToOne(mappedBy = "perfil")
    private Usuario usuario;


}
