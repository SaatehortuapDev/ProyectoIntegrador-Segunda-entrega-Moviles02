package com.grupo2.EduPerformance.EduPerformance.service;


import com.grupo2.EduPerformance.EduPerformance.model.entity.Usuario;
import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.request.AsistenciaRequestDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.response.AsistenciaResponseDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Asistencia;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Cursos;
import com.grupo2.EduPerformance.EduPerformance.model.entity.Estudiante;
import com.grupo2.EduPerformance.EduPerformance.repository.AsistenciaRepository;
import com.grupo2.EduPerformance.EduPerformance.repository.CursosRepository;
import com.grupo2.EduPerformance.EduPerformance.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository repository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CursosRepository cursosRepository;

    // ── Mapeo Entidad → ResponseDTO ──────────────────────────
    private AsistenciaResponseDTO toResponseDTO(Asistencia a) {
        AsistenciaResponseDTO dto = new AsistenciaResponseDTO();
        dto.setId(a.getId());
        dto.setFecha(a.getFecha());
        dto.setPresente(a.getPresente());

        if (a.getEstudiante() != null && a.getEstudiante().getUsuario() != null) {
            Usuario u = a.getEstudiante().getUsuario();
            dto.setNombreEstudiante(u.getNombre() + " " + u.getApellido());
        }
        if (a.getCurso() != null) {
            dto.setNombreCurso(a.getCurso().getNombre());
        }
        return dto;
    }

    // ── Mapeo RequestDTO → Entidad ───────────────────────────
    private Asistencia toEntity(AsistenciaRequestDTO dto) {
        Asistencia asistencia = new Asistencia();
        asistencia.setFecha(dto.getFecha());
        asistencia.setPresente(dto.getPresente());

        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RuntimeException(
                        "Estudiante no encontrado con ID: " + dto.getEstudianteId()));
        asistencia.setEstudiante(estudiante);

        Cursos curso = cursosRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new RuntimeException(
                        "Curso no encontrado con ID: " + dto.getCursoId()));
        asistencia.setCurso(curso);

        return asistencia;
    }

    // ── Métodos CRUD ─────────────────────────────────────────

    public List<AsistenciaResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<AsistenciaResponseDTO> findById(Long id) {
        return repository.findById(id).map(this::toResponseDTO);
    }

    @Transactional
    public AsistenciaResponseDTO save(AsistenciaRequestDTO dto) {
        // Validación de negocio — fecha futura no permitida
        if (dto.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "No se puede registrar asistencia en una fecha futura.");
        }
        return toResponseDTO(repository.save(toEntity(dto)));
    }

    @Transactional
    public AsistenciaResponseDTO update(Long id, AsistenciaRequestDTO dto) {
        Asistencia existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Asistencia no encontrada con ID: " + id));

        if (dto.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "No se puede registrar asistencia en una fecha futura.");
        }

        // Actualiza campo a campo — no reemplaza el objeto completo
        existente.setFecha(dto.getFecha());
        existente.setPresente(dto.getPresente());

        Estudiante est = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado."));
        existente.setEstudiante(est);

        Cursos curso = cursosRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado."));
        existente.setCurso(curso);

        return toResponseDTO(repository.save(existente));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<AsistenciaResponseDTO> findByCursoAndFecha(
            Long cursoId, LocalDate fecha) {
        return repository.findByCursoIdAndFecha(cursoId, fecha).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}