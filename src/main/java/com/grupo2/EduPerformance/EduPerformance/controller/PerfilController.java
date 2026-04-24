package com.grupo2.EduPerformance.EduPerformance.controller;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Perfil;
import com.grupo2.EduPerformance.EduPerformance.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para manejar los Perfiles.
@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    // Inyección del servicio de perfiles.
    @Autowired
    private PerfilService service;

    // Obtiene todos los perfiles.
    @GetMapping
    public List<Perfil> getAll() {
        return service.findAll();
    }

    // Obtiene un perfil por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Perfil> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crea un nuevo perfil.
    @PostMapping
    public Perfil create(@RequestBody Perfil entity) {
        return service.save(entity);
    }

    // Actualiza un perfil existente.
    @PutMapping("/{id}")
    public ResponseEntity<Perfil> update(@PathVariable Long id, @RequestBody Perfil entity) {
        try {
            Perfil updated = service.update(id, entity);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina un perfil por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
