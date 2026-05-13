package com.grupo2.EduPerformance.EduPerformance.model.entity.dto.response;

import lombok.*;

// Lo que el cliente recibe al consultar una Calificacion.
// Muestra nombres legibles en lugar de objetos anidados.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalificacionResponseDTO {

    private Long id;
    private Double nota;

    // Nombre del estudiante — legible directamente, sin anidar el objeto
    private String nombreEstudiante;

    // Nombre del curso
    private String nombreCurso;
}
