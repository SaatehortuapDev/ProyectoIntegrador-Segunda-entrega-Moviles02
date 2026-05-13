package com.grupo2.EduPerformance.EduPerformance.model.entity.dto.response;

import lombok.*;
import java.util.List;

// Lo que el cliente recibe al consultar un Estudiante.
// Aplana los datos del Usuario y muestra solo nombres de cursos.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteResponseDTO {

    private Long id;

    // Datos del usuario aplanados — sin anidar el objeto Usuario completo
    private String nombreCompleto;   // nombre + " " + apellido
    private String email;
    private Integer edad;

    // Solo nombres de cursos — no los objetos Cursos completos.
    // Evita el bucle: Estudiante → Cursos → Usuario → Cursos → ...
    private List<String> cursos;
}
