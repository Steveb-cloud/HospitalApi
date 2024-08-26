package com.hospitala.hospitalapi.controllers;

import com.hospitala.hospitalapi.DTO.NotificacioneDTO;
import com.hospitala.hospitalapi.models.Medico;
import com.hospitala.hospitalapi.models.Notificacione;
import com.hospitala.hospitalapi.models.Paciente;
import com.hospitala.hospitalapi.repositories.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionesController {

    @Autowired
    private NotificacionRepository notificacioneRepository;

    @GetMapping
    public ResponseEntity<List<NotificacioneDTO>> getNotificaciones() {
        List<Notificacione> notificaciones = notificacioneRepository.findAll();
        List<NotificacioneDTO> notificacionesDTO = notificaciones.stream().map(n -> {
            NotificacioneDTO dto = new NotificacioneDTO();
            dto.setId(n.getId());
            dto.setPacienteId(n.getPaciente() != null ? n.getPaciente().getId() : null);
            dto.setMedicoId(n.getMedico() != null ? n.getMedico().getId() : null);
            dto.setMensaje(n.getMensaje());
            dto.setFechaEnvio(n.getFechaEnvio());
            dto.setTipo(n.getTipo());
            dto.setEstado(n.getEstado());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(notificacionesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacioneDTO> getNotificacione(@PathVariable Integer id) {
        Optional<Notificacione> notificacioneOpt = notificacioneRepository.findById(id);

        if (notificacioneOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Notificacione n = notificacioneOpt.get();
        NotificacioneDTO dto = new NotificacioneDTO();
        dto.setId(n.getId());
        dto.setPacienteId(n.getPaciente() != null ? n.getPaciente().getId() : null);
        dto.setMedicoId(n.getMedico() != null ? n.getMedico().getId() : null);
        dto.setMensaje(n.getMensaje());
        dto.setFechaEnvio(n.getFechaEnvio());
        dto.setTipo(n.getTipo());
        dto.setEstado(n.getEstado());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putNotificacione(@PathVariable Integer id, @RequestBody NotificacioneDTO dto) {
        Optional<Notificacione> notificacioneOpt = notificacioneRepository.findById(id);

        if (notificacioneOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Notificacione notificacione = notificacioneOpt.get();
        notificacione.setPaciente(dto.getPacienteId() != null ? new Paciente(dto.getPacienteId()) : null);
        notificacione.setMedico(dto.getMedicoId() != null ? new Medico(dto.getMedicoId()) : null);
        notificacione.setMensaje(dto.getMensaje());
        notificacione.setFechaEnvio(dto.getFechaEnvio());
        notificacione.setTipo(dto.getTipo());
        notificacione.setEstado(dto.getEstado());

        notificacioneRepository.save(notificacione);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<NotificacioneDTO> postNotificacione(@RequestBody NotificacioneDTO dto) {
        Notificacione notificacione = new Notificacione();
        notificacione.setPaciente(dto.getPacienteId() != null ? new Paciente(dto.getPacienteId()) : null);
        notificacione.setMedico(dto.getMedicoId() != null ? new Medico(dto.getMedicoId()) : null);
        notificacione.setMensaje(dto.getMensaje());
        notificacione.setFechaEnvio(dto.getFechaEnvio());
        notificacione.setTipo(dto.getTipo());
        notificacione.setEstado(dto.getEstado());

        notificacioneRepository.save(notificacione);

        dto.setId(notificacione.getId());

        return ResponseEntity.status(201).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacione(@PathVariable Integer id) {
        if (!notificacioneRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        notificacioneRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

