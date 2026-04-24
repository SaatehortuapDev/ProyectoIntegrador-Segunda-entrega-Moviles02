package com.grupo2.EduPerformance.EduPerformance.controller;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Cursos;
import com.grupo2.EduPerformance.EduPerformance.service.CursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para manejar los Cursos.
@RestController
@RequestMapping("/api/cursos")
public class CursosController {

    // Inyección del servicio de cursos.
    @Autowired
    private CursosService service;

    // Obtiene todos los cursos.
    @GetMapping
    public List<Cursos> getAll() {
        return service.findAll();
    }

    // Obtiene un curso por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Cursos> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crea un nuevo curso.
    @PostMapping
    public Cursos create(@RequestBody Cursos entity) {
        return service.save(entity);
    }

    // Actualiza un curso existente.
    @PutMapping("/{id}")
    public ResponseEntity<Cursos> update(@PathVariable Long id, @RequestBody Cursos entity) {
        try {
            Cursos updated = service.update(id, entity);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina un curso por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
