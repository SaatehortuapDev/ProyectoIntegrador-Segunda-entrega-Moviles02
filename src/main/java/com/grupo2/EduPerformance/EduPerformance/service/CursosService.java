package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Cursos;
import com.grupo2.EduPerformance.EduPerformance.repository.CursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio para manejar la lógica de Cursos.
@Service
public class CursosService {

    // Inyección del repositorio de cursos.
    @Autowired
    private CursosRepository repository;

    // Retorna todos los cursos.
    public List<Cursos> findAll() {
        return repository.findAll();
    }

    // Busca un curso por su ID.
    public Optional<Cursos> findById(Long id) {
        return repository.findById(id);
    }

    // Guarda un nuevo curso.
    public Cursos save(Cursos entity) {
        return repository.save(entity);
    }

    // Actualiza un curso existente.
    public Cursos update(Long id, Cursos entity) {
        return repository.findById(id).map(existingEntity -> {
            entity.setId(id);
            return repository.save(entity);
        }).orElseThrow(() -> new RuntimeException("Entity no encontrada con ID: " + id));
    }

    // Elimina un curso por su ID.
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
