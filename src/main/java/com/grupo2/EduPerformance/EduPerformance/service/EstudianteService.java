package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Estudiante;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Cursos;
import com.grupo2.EduPerformance.EduPerformance.repository.EstudianteRepository;
import com.grupo2.EduPerformance.EduPerformance.repository.CursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio para manejar lógica de Estudiantes.
@Service
public class EstudianteService {

    // Inyección del repositorio de estudiantes.
    @Autowired
    private EstudianteRepository repository;
    
    // Inyección del repositorio de cursos para la matrícula.
    @Autowired
    private CursosRepository cursosRepository;

    // Retorna todos los estudiantes.
    public List<Estudiante> findAll() {
        return repository.findAll();
    }

    // Busca un estudiante por su ID.
    public Optional<Estudiante> findById(Long id) {
        return repository.findById(id);
    }

    // Guarda un nuevo estudiante.
    public Estudiante save(Estudiante entity) {
        return repository.save(entity);
    }

    // Actualiza un estudiante existente.
    public Estudiante update(Long id, Estudiante entity) {
        return repository.findById(id).map(existingEntity -> {
            entity.setId(id);
            return repository.save(entity);
        }).orElseThrow(() -> new RuntimeException("Entity no encontrada con ID: " + id));
    }

    // Elimina un estudiante por su ID.
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Busca estudiantes inscritos en un curso específico.
    public List<Estudiante> findByCurso(Long cursoId) {
        return repository.findByCursos_Id(cursoId);
    }

    // Matricula a un estudiante validando que no esté duplicado.
    public Estudiante matricularEstudiante(Long estudianteId, Long cursoId) {
        Estudiante estudiante = repository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado."));

        Cursos curso = cursosRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado."));

        if(estudiante.getCursos().contains(curso)) {
            throw new com.grupo2.EduPerformance.EduPerformance.exception.ReglaNegocioException("El estudiante ya está matriculado en este curso.");
        }

        estudiante.getCursos().add(curso);
        return repository.save(estudiante);
    }
}
