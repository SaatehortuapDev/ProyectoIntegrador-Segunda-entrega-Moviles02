package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.model.entity.Usuario;
import com.grupo2.EduPerformance.EduPerformance.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio para manejar la lógica de Usuarios.
@Service
public class UsuarioService {

    // Inyección del repositorio de usuarios.
    @Autowired
    private UsuarioRepository repository;

    // Retorna todos los usuarios.
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    // Busca un usuario por su ID.
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    // Guarda un nuevo usuario.
    public Usuario save(Usuario entity) {
        return repository.save(entity);
    }

    // Actualiza un usuario existente.
    public Usuario update(Long id, Usuario entity) {
        return repository.findById(id).map(existingEntity -> {
            entity.setId(id);
            return repository.save(entity);
        }).orElseThrow(() -> new RuntimeException("Entity no encontrada con ID: " + id));
    }

    // Elimina un usuario por su ID.
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
