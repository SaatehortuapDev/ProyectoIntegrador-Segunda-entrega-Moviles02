package com.grupo2.EduPerformance.EduPerformance.controller;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Asistencia;
import com.grupo2.EduPerformance.EduPerformance.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.time.LocalDate;

// Controlador para manejar las Asistencias.
@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    // Inyección del servicio de asistencias.
    @Autowired
    private AsistenciaService service;

    // Obtiene todas las asistencias.
    @GetMapping
    public List<Asistencia> getAll() {
        return service.findAll();
    }

    // Obtiene una asistencia por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crea una nueva asistencia.
    @PostMapping
    public Asistencia create(@RequestBody Asistencia entity) {
        return service.save(entity);
    }

    // Actualiza una asistencia existente.
    @PutMapping("/{id}")
    public ResponseEntity<Asistencia> update(@PathVariable Long id, @RequestBody Asistencia entity) {
        try {
            Asistencia updated = service.update(id, entity);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina una asistencia por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Busca asistencias filtradas por curso y fecha.
    @GetMapping("/curso/{cursoId}/fecha/{fecha}")
    public ResponseEntity<List<Asistencia>> getByCursoAndFecha(
            @PathVariable Long cursoId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.findByCursoAndFecha(cursoId, fecha));
    }
}
