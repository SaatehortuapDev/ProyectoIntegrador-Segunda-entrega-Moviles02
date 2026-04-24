package com.grupo2.EduPerformance.EduPerformance.controller;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Calificacion;
import com.grupo2.EduPerformance.EduPerformance.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para manejar las Calificaciones.
@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    // Inyección del servicio de calificaciones.
    @Autowired
    private CalificacionService service;

    // Obtiene todas las calificaciones.
    @GetMapping
    public List<Calificacion> getAll() {
        return service.findAll();
    }

    // Obtiene una calificación por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crea una nueva calificación.
    @PostMapping
    public Calificacion create(@RequestBody Calificacion entity) {
        return service.save(entity);
    }

    // Actualiza una calificación existente.
    @PutMapping("/{id}")
    public ResponseEntity<Calificacion> update(@PathVariable Long id, @RequestBody Calificacion entity) {
        try {
            Calificacion updated = service.update(id, entity);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina una calificación por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtiene calificaciones por estudiante y curso.
    @GetMapping("/estudiante/{estudianteId}/curso/{cursoId}")
    public ResponseEntity<List<Calificacion>> getByEstudianteAndCurso(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        return ResponseEntity.ok(service.findByEstudianteAndCurso(estudianteId, cursoId));
    }

    // Calcula el promedio de notas de un estudiante en un curso.
    @GetMapping("/promedio/estudiante/{estudianteId}/curso/{cursoId}")
    public ResponseEntity<Double> getPromedio(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        return ResponseEntity.ok(service.calcularPromedio(estudianteId, cursoId));
    }
}
