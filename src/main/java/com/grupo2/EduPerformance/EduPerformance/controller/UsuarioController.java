package com.grupo2.EduPerformance.EduPerformance.controller;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Usuario;
import com.grupo2.EduPerformance.EduPerformance.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para manejar los Usuarios.
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    // Inyección del servicio de usuarios.
    @Autowired
    private UsuarioService service;

    // Obtiene todos los usuarios.
    @GetMapping
    public List<Usuario> getAll() {
        return service.findAll();
    }

    // Obtiene un usuario por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crea un nuevo usuario.
    @PostMapping
    public Usuario create(@RequestBody Usuario entity) {
        return service.save(entity);
    }

    // Actualiza un usuario existente.
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario entity) {
        try {
            Usuario updated = service.update(id, entity);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina un usuario por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
