package com.grupo2.EduPerformance.EduPerformance.controller;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Profesor;
import com.grupo2.EduPerformance.EduPerformance.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para manejar los Profesores.
@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    // Inyección del servicio de profesores.
    @Autowired
    private ProfesorService service;

    // Obtiene todos los profesores.
    @GetMapping
    public List<Profesor> getAll() {
        return service.findAll();
    }

    // Obtiene un profesor por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crea un nuevo profesor.
    @PostMapping
    public Profesor create(@RequestBody Profesor entity) {
        return service.save(entity);
    }

    // Actualiza un profesor existente.
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> update(@PathVariable Long id, @RequestBody Profesor entity) {
        try {
            Profesor updated = service.update(id, entity);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina un profesor por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Asigna un curso a un profesor.
    @PostMapping("/{id}/asignar/{cursoId}")
    public ResponseEntity<Profesor> asignarCurso(@PathVariable Long id, @PathVariable Long cursoId) {
        try {
            return ResponseEntity.ok(service.asignarCurso(id, cursoId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
