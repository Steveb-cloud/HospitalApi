package com.hospitala.hospitalapi.controllers;

import com.hospitala.hospitalapi.DTO.TransferenciasPacienteDTO;
import com.hospitala.hospitalapi.models.Paciente;
import com.hospitala.hospitalapi.models.Transferenciaspaciente;
import com.hospitala.hospitalapi.repositories.PacienteRepository;
import com.hospitala.hospitalapi.repositories.TransferenciaspacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transferenciaspacientes")
public class TransferenciaspacientesController {

    @Autowired
    private TransferenciaspacienteRepository transferenciaspacienteRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // GET: api/Transferenciaspacientes
    @GetMapping
    public List<TransferenciasPacienteDTO> getTransferenciaspacientes() {
        return transferenciaspacienteRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // GET: api/Transferenciaspacientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TransferenciasPacienteDTO> getTransferenciaspaciente(@PathVariable Integer id) {
        Optional<Transferenciaspaciente> transferenciaspaciente = transferenciaspacienteRepository.findById(id);

        return transferenciaspaciente.map(tp -> ResponseEntity.ok(convertToDTO(tp)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: api/Transferenciaspacientes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTransferenciaspaciente(@PathVariable Integer id, @RequestBody TransferenciasPacienteDTO transferenciasPacienteDTO) {
        if (!transferenciaspacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Transferenciaspaciente transferenciaspaciente = transferenciaspacienteRepository.findById(id).orElseThrow();
        updateEntityFromDTO(transferenciasPacienteDTO, transferenciaspaciente);
        transferenciaspacienteRepository.save(transferenciaspaciente);

        return ResponseEntity.noContent().build();
    }

    // POST: api/Transferenciaspacientes
    @PostMapping
    public ResponseEntity<TransferenciasPacienteDTO> createTransferenciaspaciente(@RequestBody TransferenciasPacienteDTO transferenciasPacienteDTO) {
        Transferenciaspaciente transferenciaspaciente = new Transferenciaspaciente();
        updateEntityFromDTO(transferenciasPacienteDTO, transferenciaspaciente);
        transferenciaspacienteRepository.save(transferenciaspaciente);

        return ResponseEntity.ok(convertToDTO(transferenciaspaciente));
    }

    // DELETE: api/Transferenciaspacientes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransferenciaspaciente(@PathVariable Integer id) {
        if (!transferenciaspacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        transferenciaspacienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TransferenciasPacienteDTO convertToDTO(Transferenciaspaciente transferenciaspaciente) {
        TransferenciasPacienteDTO dto = new TransferenciasPacienteDTO();
        dto.setId(transferenciaspaciente.getId());
        dto.setPacienteId(transferenciaspaciente.getPaciente().getId());
        dto.setHospitalOrigen(transferenciaspaciente.getHospitalOrigen());
        dto.setHospitalDestino(transferenciaspaciente.getHospitalDestino());
        dto.setFechaSolicitud(transferenciaspaciente.getFechaSolicitud());
        dto.setEstado(transferenciaspaciente.getEstado());
        return dto;
    }

    private void updateEntityFromDTO(TransferenciasPacienteDTO dto, Transferenciaspaciente transferenciaspaciente) {
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente not found"));
        transferenciaspaciente.setPaciente(paciente);
        transferenciaspaciente.setHospitalOrigen(dto.getHospitalOrigen());
        transferenciaspaciente.setHospitalDestino(dto.getHospitalDestino());
        transferenciaspaciente.setFechaSolicitud(dto.getFechaSolicitud());
        transferenciaspaciente.setEstado(dto.getEstado());
    }
}


