package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Asistencia;
import com.grupo2.EduPerformance.EduPerformance.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

// Servicio para manejar la lógica de Asistencias.
@Service
public class AsistenciaService {

    // Inyección del repositorio de asistencias.
    @Autowired
    private AsistenciaRepository repository;

    // Retorna todas las asistencias.
    public List<Asistencia> findAll() {
        return repository.findAll();
    }

    // Busca una asistencia por su ID.
    public Optional<Asistencia> findById(Long id) {
        return repository.findById(id);
    }

    // Guarda asistencia validando que no sea en fecha futura.
    public Asistencia save(Asistencia entity) {
        if(entity.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede registrar asistencia en una fecha futura.");
        }
        return repository.save(entity);
    }

    // Actualiza una asistencia existente.
    public Asistencia update(Long id, Asistencia entity) {
        return repository.findById(id).map(existingEntity -> {
            entity.setId(id);
            return repository.save(entity);
        }).orElseThrow(() -> new RuntimeException("Entity no encontrada con ID: " + id));
    }

    // Elimina una asistencia por su ID.
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Busca asistencias filtradas por curso y fecha.
    public List<Asistencia> findByCursoAndFecha(Long cursoId, LocalDate fecha) {
        return repository.findByCursoIdAndFecha(cursoId, fecha);
    }
}
