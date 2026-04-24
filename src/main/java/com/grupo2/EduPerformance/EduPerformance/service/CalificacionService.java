package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Calificacion;
import com.grupo2.EduPerformance.EduPerformance.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio con lógica de negocio para Calificaciones.
@Service
public class CalificacionService {

    // Inyección del repositorio de calificaciones.
    @Autowired
    private CalificacionRepository repository;

    // Retorna todas las calificaciones.
    public List<Calificacion> findAll() {
        return repository.findAll();
    }

    // Busca una calificación por su ID.
    public Optional<Calificacion> findById(Long id) {
        return repository.findById(id);
    }

    // Guarda calificación. Nota debe ser entre 0.0 y 5.0.
    public Calificacion save(Calificacion entity) {
        if(entity.getNota() < 0.0 || entity.getNota() > 5.0) {
            throw new com.grupo2.EduPerformance.EduPerformance.exception.ReglaNegocioException("La nota debe estar entre 0.0 y 5.0");
        }
        return repository.save(entity);
    }

    // Actualiza una calificación existente.
    public Calificacion update(Long id, Calificacion entity) {
        return repository.findById(id).map(existingEntity -> {
            entity.setId(id);
            return repository.save(entity);
        }).orElseThrow(() -> new RuntimeException("Entity no encontrada con ID: " + id));
    }

    // Elimina una calificación por su ID.
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Busca calificaciones por estudiante y curso.
    public List<Calificacion> findByEstudianteAndCurso(Long estudianteId, Long cursoId) {
        return repository.findByEstudianteIdAndCursoId(estudianteId, cursoId);
    }

    // Calcula el promedio de notas de un estudiante en un curso.
    public double calcularPromedio(Long estudianteId, Long cursoId) {
        List<Calificacion> notas = repository.findByEstudianteIdAndCursoId(estudianteId, cursoId);
        if (notas.isEmpty()) return 0.0;

        double suma = notas.stream().mapToDouble(Calificacion::getNota).sum();
        return suma / notas.size();
    }
}
