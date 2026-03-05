package com.grupo2.EduPerformance.EduPerformance.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "cursos")
public class Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY para rendimiento
    @JoinColumn(name = "usuario_id") // FK en la tabla productos
    @JsonBackReference // Evita recursión infinita al serializar
    private Usuario usuario;

}
