package com.grupo2.EduPerformance.EduPerformance.service;

import com.grupo2.EduPerformance.EduPerformance.exception.ReglaNegocioException;
import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.request.ProfesorRequestDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.response.ProfesorResponseDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Cursos;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Profesor;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Usuario;
import com.grupo2.EduPerformance.EduPerformance.repository.CursosRepository;
import com.grupo2.EduPerformance.EduPerformance.repository.ProfesorRepository;
import com.grupo2.EduPerformance.EduPerformance.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository repository;

    @Autowired
    private CursosRepository cursosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ── Mapeo Entidad → ResponseDTO ──────────────────────────
    private ProfesorResponseDTO toResponseDTO(Profesor p) {
        ProfesorResponseDTO dto = new ProfesorResponseDTO();
        dto.setId(p.getId());

        if (p.getUsuario() != null) {
            Usuario u = p.getUsuario();
            dto.setNombreCompleto(u.getNombre() + " " + u.getApellido());
            dto.setEmail(u.getEmail());
            dto.setEdad(u.getEdad());
        }

        // Lista de nombres de cursos que dicta — no objetos completos
        if (p.getCursos() != null) {
            dto.setCursos(
                    p.getCursos().stream()
                            .map(Cursos::getNombre)
                            .collect(Collectors.toList()));
        }

        return dto;
    }

    // ── Mapeo RequestDTO → Entidad ───────────────────────────
    private Profesor toEntity(ProfesorRequestDTO dto) {
        Profesor profesor = new Profesor();
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con ID: " + dto.getUsuarioId()));
        profesor.setUsuario(usuario);
        return profesor;
    }

    // ── Métodos CRUD ─────────────────────────────────────────

    public List<ProfesorResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProfesorResponseDTO> findById(Long id) {
        return repository.findById(id).map(this::toResponseDTO);
    }

    @Transactional
    public ProfesorResponseDTO save(ProfesorRequestDTO dto) {
        return toResponseDTO(repository.save(toEntity(dto)));
    }

    @Transactional
    public ProfesorResponseDTO update(Long id, ProfesorRequestDTO dto) {
        Profesor existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Profesor no encontrado con ID: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con ID: " + dto.getUsuarioId()));

        // Conserva los cursos ya asignados — solo actualiza el usuario
        existente.setUsuario(usuario);
        return toResponseDTO(repository.save(existente));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // ── Lógica de negocio: asignación de curso ───────────────
    @Transactional
    public ProfesorResponseDTO asignarCurso(Long profesorId, Long cursoId) {
        Profesor profesor = repository.findById(profesorId)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado."));

        Cursos curso = cursosRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado."));

        // Corregido: usa ReglaNegocioException igual que EstudianteService
        // (antes usaba RuntimeException genérica — inconsistencia del proyecto
        // original)
        if (profesor.getCursos().contains(curso)) {
            throw new ReglaNegocioException(
                    "El profesor ya está asignado a este curso.");
        }

        profesor.getCursos().add(curso);
        return toResponseDTO(repository.save(profesor));
    }
}