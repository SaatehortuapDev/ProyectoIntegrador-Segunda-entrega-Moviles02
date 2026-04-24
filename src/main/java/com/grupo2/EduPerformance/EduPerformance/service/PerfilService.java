package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Perfil;
import com.grupo2.EduPerformance.EduPerformance.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio para manejar la lógica de Perfiles.
@Service
public class PerfilService {

    // Inyección del repositorio de perfiles.
    @Autowired
    private PerfilRepository repository;

    // Retorna todos los perfiles.
    public List<Perfil> findAll() {
        return repository.findAll();
    }

    // Busca un perfil por su ID.
    public Optional<Perfil> findById(Long id) {
        return repository.findById(id);
    }

    // Guarda un nuevo perfil.
    public Perfil save(Perfil entity) {
        return repository.save(entity);
    }

    // Actualiza un perfil existente.
    public Perfil update(Long id, Perfil entity) {
        return repository.findById(id).map(existingEntity -> {
            entity.setId(id);
            return repository.save(entity);
        }).orElseThrow(() -> new RuntimeException("Entity no encontrada con ID: " + id));
    }

    // Elimina un perfil por su ID.
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
