package com.grupo2.EduPerformance.EduPerformance.controller;

import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.request.PerfilRequestDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.response.PerfilResponseDTO;
import com.grupo2.EduPerformance.EduPerformance.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService service;

    @GetMapping
    public List<PerfilResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PerfilResponseDTO create(@Valid @RequestBody PerfilRequestDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PerfilRequestDTO dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}