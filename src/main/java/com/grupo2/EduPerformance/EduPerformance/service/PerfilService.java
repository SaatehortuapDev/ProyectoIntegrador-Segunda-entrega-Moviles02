package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.request.PerfilRequestDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.response.PerfilResponseDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Perfil;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Usuario;
import com.grupo2.EduPerformance.EduPerformance.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository repository;

    // ── Mapeo Entidad → ResponseDTO ──────────────────────────
    private PerfilResponseDTO toResponseDTO(Perfil p) {
        PerfilResponseDTO dto = new PerfilResponseDTO();
        dto.setId(p.getId());
        dto.setDireccion(p.getDireccion());
        dto.setTelefono(p.getTelefono());

        // Navega la relación inversa hacia Usuario de forma segura
        // Perfil tiene @OneToOne(mappedBy = "perfil") → accede al usuario dueño
        if (p.getUsuario() != null) {
            Usuario u = p.getUsuario();
            dto.setUsuarioId(u.getId());
            dto.setNombreUsuario(u.getNombre() + " " + u.getApellido());
        }

        return dto;
    }

    // ── Mapeo RequestDTO → Entidad ───────────────────────────
    // Perfil no necesita referencias a otras entidades en su creación —
    // solo dirección y teléfono. La relación con Usuario se gestiona
    // desde UsuarioService al hacer cascade.
    private Perfil toEntity(PerfilRequestDTO dto) {
        Perfil perfil = new Perfil();
        perfil.setDireccion(dto.getDireccion());
        perfil.setTelefono(dto.getTelefono());
        return perfil;
    }

    // ── Métodos CRUD ─────────────────────────────────────────

    public List<PerfilResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<PerfilResponseDTO> findById(Long id) {
        return repository.findById(id).map(this::toResponseDTO);
    }

    @Transactional
    public PerfilResponseDTO save(PerfilRequestDTO dto) {
        return toResponseDTO(repository.save(toEntity(dto)));
    }

    @Transactional
    public PerfilResponseDTO update(Long id, PerfilRequestDTO dto) {
        Perfil existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Perfil no encontrado con ID: " + id));

        // Actualiza campo a campo — conserva el usuario vinculado
        // No se toca existente.getUsuario() porque la relación
        // la maneja Usuario con CascadeType.ALL
        existente.setDireccion(dto.getDireccion());
        existente.setTelefono(dto.getTelefono());

        return toResponseDTO(repository.save(existente));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}