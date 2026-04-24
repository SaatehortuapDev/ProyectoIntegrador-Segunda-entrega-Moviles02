package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Profesor;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Cursos;
import com.grupo2.EduPerformance.EduPerformance.repository.ProfesorRepository;
import com.grupo2.EduPerformance.EduPerformance.repository.CursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio para manejar la lógica de Profesores.
@Service
public class ProfesorService {

    // Inyección del repositorio de profesores.
    @Autowired
    private ProfesorRepository repository;

    // Inyección del repositorio de cursos.
    @Autowired
    private CursosRepository cursosRepository;

    // Retorna todos los profesores.
    public List<Profesor> findAll() {
        return repository.findAll();
    }

    // Busca un profesor por su ID.
    public Optional<Profesor> findById(Long id) {
        return repository.findById(id);
    }

    // Guarda un nuevo profesor.
    public Profesor save(Profesor entity) {
        return repository.save(entity);
    }

    // Actualiza un profesor existente.
    public Profesor update(Long id, Profesor entity) {
        return repository.findById(id).map(existingEntity -> {
            entity.setId(id);
            return repository.save(entity);
        }).orElseThrow(() -> new RuntimeException("Entity no encontrada con ID: " + id));
    }

    // Elimina un profesor por su ID.
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Asigna un curso a un profesor validando duplicados.
    public Profesor asignarCurso(Long profesorId, Long cursoId) {
        Profesor profesor = repository.findById(profesorId)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado."));

        Cursos curso = cursosRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado."));

        if(profesor.getCursos().contains(curso)) {
            throw new RuntimeException("El profesor ya está asignado a este curso.");
        }

        profesor.getCursos().add(curso);
        return repository.save(profesor);
    }
}
