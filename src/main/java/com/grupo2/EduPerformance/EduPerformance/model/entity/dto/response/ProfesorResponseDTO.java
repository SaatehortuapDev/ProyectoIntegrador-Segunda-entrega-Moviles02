package com.grupo2.EduPerformance.EduPerformance.model.entity.dto.response;

import lombok.*;
import java.util.List;

// Lo que el cliente recibe al consultar un Profesor.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfesorResponseDTO {

    private Long id;

    // Datos del usuario aplanados
    private String nombreCompleto;
    private String email;
    private Integer edad;

    // Nombres de los cursos que dicta — no objetos completos
    private List<String> cursos;
}
