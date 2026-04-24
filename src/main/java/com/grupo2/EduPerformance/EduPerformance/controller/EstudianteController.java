package com.grupo2.EduPerformance.EduPerformance.controller;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Estudiante;
import com.grupo2.EduPerformance.EduPerformance.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para manejar los Estudiantes.
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    // Inyección del servicio de estudiantes.
    @Autowired
    private EstudianteService service;

    // Obtiene todos los estudiantes.
    @GetMapping
    public List<Estudiante> getAll() {
        return service.findAll();
    }

    // Obtiene un estudiante por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crea un nuevo estudiante.
    @PostMapping
    public Estudiante create(@RequestBody Estudiante entity) {
        return service.save(entity);
    }

    // Actualiza un estudiante existente.
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> update(@PathVariable Long id, @RequestBody Estudiante entity) {
        try {
            Estudiante updated = service.update(id, entity);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina un estudiante por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtiene estudiantes por curso.
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Estudiante>> getByCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(service.findByCurso(cursoId));
    }

    // Matricula un estudiante en un curso.
    @PostMapping("/{id}/matricular/{cursoId}")
    public ResponseEntity<Estudiante> matricular(@PathVariable Long id, @PathVariable Long cursoId) {
        try {
            return ResponseEntity.ok(service.matricularEstudiante(id, cursoId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
