package com.grupo2.EduPerformance.EduPerformance.model.entity;

import jakarta.persistence.*;
import lombok.*;

// Entidad Calificacion.
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "calificaciones")
public class Calificacion {

    // Identificador único de la calificación.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Valor numérico de la nota.
    private Double nota;

    // Relación con el estudiante evaluado.
    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    // Relación con el curso correspondiente.
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Cursos curso;
}
