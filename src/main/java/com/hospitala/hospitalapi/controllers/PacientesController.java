package com.hospitala.hospitalapi.controllers;

import com.hospitala.hospitalapi.DTO.PacienteDTO;
import com.hospitala.hospitalapi.models.Paciente;
import com.hospitala.hospitalapi.repositories.PacienteRepository;
import com.hospitala.hospitalapi.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pacientes")
public class PacientesController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteService pacienteTransferService;

    // GET: api/pacientes
    @GetMapping
    public List<PacienteDTO> getPacientes() {
        return pacienteRepository.findAll().stream()
                .map(this::convertToPacienteDTO)
                .collect(Collectors.toList());
    }

    // GET: api/pacientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getPaciente(@PathVariable int id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);

        if (paciente.isPresent()) {
            return ResponseEntity.ok(convertToPacienteDTO(paciente.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT: api/pacientes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> putPaciente(@PathVariable int id, @RequestBody PacienteDTO pacienteDTO) {
        if (id != pacienteDTO.getId()) {
            return ResponseEntity.badRequest().build();
        }

        if (!pacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Paciente paciente = convertToPaciente(pacienteDTO);
        pacienteRepository.save(paciente);

        return ResponseEntity.noContent().build();
    }

    // POST: api/pacientes
    @PostMapping
    public ResponseEntity<PacienteDTO> postPaciente(@RequestBody PacienteDTO pacienteDTO) {
        Paciente paciente = convertToPaciente(pacienteDTO);
        paciente = pacienteRepository.save(paciente);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(paciente.getId())
                        .toUri()
        ).body(convertToPacienteDTO(paciente));
    }

    // DELETE: api/pacientes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable int id) {
        if (!pacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        pacienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // POST: api/pacientes/transferir/{id}
    @PostMapping("/transferir/{id}")
    public ResponseEntity<String> transferirPaciente(@PathVariable int id) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);

        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            PacienteDTO pacienteDTO = convertToPacienteDTO(paciente);
            String resultado = pacienteTransferService.transferirPaciente(pacienteDTO);
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private PacienteDTO convertToPacienteDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setTelefono(paciente.getTelefono());
        dto.setDireccion(paciente.getDireccion());
        dto.setEstado(paciente.getEstado());
        return dto;
    }

    private Paciente convertToPaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDTO.getId());
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setApellido(pacienteDTO.getApellido());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setDireccion(pacienteDTO.getDireccion());
        paciente.setEstado(pacienteDTO.getEstado());
        return paciente;
    }
}

