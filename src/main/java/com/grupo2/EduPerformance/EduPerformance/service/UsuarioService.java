package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.request.UsuarioRequestDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.response.UsuarioResponseDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Usuario;
import com.grupo2.EduPerformance.EduPerformance.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    // BCrypt es el estándar para hashear passwords.
    // Genera un hash distinto cada vez (salt automático) — seguro contra rainbow
    // tables.
    // El "10" es el factor de costo — más alto = más seguro pero más lento.
@Autowired
private PasswordEncoder passwordEncoder;

    // ── Mapeo Entidad → ResponseDTO ──────────────────────────
    private UsuarioResponseDTO toResponseDTO(Usuario u) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(u.getId());
        dto.setNombreCompleto(u.getNombre() + " " + u.getApellido());
        dto.setEdad(u.getEdad());
        dto.setEmail(u.getEmail());
        // ✅ password NO se mapea al ResponseDTO — nunca sale de la BD hacia el cliente

        if (u.getPerfil() != null) {
            dto.setDireccion(u.getPerfil().getDireccion());
            dto.setTelefono(u.getPerfil().getTelefono());
        }
        return dto;
    }

    // ── Mapeo RequestDTO → Entidad ───────────────────────────
    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEdad(dto.getEdad());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword())); // 🔐 En un caso real, aquí se debería hashear


        return usuario;
    }

    // ── Métodos CRUD ─────────────────────────────────────────

    public List<UsuarioResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> findById(Long id) {
        return repository.findById(id).map(this::toResponseDTO);
    }


    @Transactional
    public UsuarioResponseDTO save(UsuarioRequestDTO dto) {
        return toResponseDTO(repository.save(toEntity(dto)));
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {
        Usuario existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con ID: " + id));

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setEdad(dto.getEdad());
        existente.setEmail(dto.getEmail());

        // 🔐 Solo re-hashea si el cliente envía un password nuevo
        // Evita re-hashear un hash ya existente si el cliente lo envía igual
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            if (!passwordEncoder.matches(dto.getPassword(), existente.getPassword())) {
                existente.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
        }

        return toResponseDTO(repository.save(existente));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}