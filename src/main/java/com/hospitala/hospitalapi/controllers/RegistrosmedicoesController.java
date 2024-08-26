package com.hospitala.hospitalapi.controllers;

import com.hospitala.hospitalapi.DTO.RegistrosMedicoDTO;
import com.hospitala.hospitalapi.models.Registrosmedico;
import com.hospitala.hospitalapi.repositories.RegistrosmedicoRepository;
import com.hospitala.hospitalapi.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registrosmedicos")
public class RegistrosmedicoesController {

    @Autowired
    private RegistrosmedicoRepository registrosmedicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // GET: api/registrosmedicos
    @GetMapping
    public List<RegistrosMedicoDTO> getRegistrosmedicos() {
        return registrosmedicoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET: api/registrosmedicos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RegistrosMedicoDTO> getRegistrosmedico(@PathVariable Integer id) {
        Optional<Registrosmedico> registrosmedico = registrosmedicoRepository.findById(id);
        return registrosmedico.map(rm -> ResponseEntity.ok(convertToDTO(rm)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: api/registrosmedicos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> putRegistrosmedico(@PathVariable Integer id, @RequestBody RegistrosMedicoDTO registrosmedicoDTO) {
        if (!registrosmedicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Registrosmedico registrosmedico = convertToEntity(registrosmedicoDTO);
        registrosmedicoRepository.save(registrosmedico);

        return ResponseEntity.noContent().build();
    }

    // POST: api/registrosmedicos
    @PostMapping
    public ResponseEntity<RegistrosMedicoDTO> postRegistrosmedico(@RequestBody RegistrosMedicoDTO registrosmedicoDTO) {
        Registrosmedico registrosmedico = convertToEntity(registrosmedicoDTO);
        registrosmedicoRepository.save(registrosmedico);

        return ResponseEntity.ok(convertToDTO(registrosmedico));
    }

    // DELETE: api/registrosmedicos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistrosmedico(@PathVariable Integer id) {
        if (!registrosmedicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        registrosmedicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Convertir entidad a DTO
    private RegistrosMedicoDTO convertToDTO(Registrosmedico registrosmedico) {
        RegistrosMedicoDTO dto = new RegistrosMedicoDTO();
        dto.setId(registrosmedico.getId());
        dto.setPacienteId(registrosmedico.getPaciente().getId());
        dto.setFecha(registrosmedico.getFecha());
        dto.setDescripcion(registrosmedico.getDescripcion());
        dto.setEstado(registrosmedico.getEstado());
        return dto;
    }

    // Convertir DTO a entidad
    private Registrosmedico convertToEntity(RegistrosMedicoDTO dto) {
        Registrosmedico registrosmedico = new Registrosmedico();
        registrosmedico.setId(dto.getId());
        registrosmedico.setPaciente(pacienteRepository.findById(dto.getPacienteId()).orElseThrow(() -> new RuntimeException("Paciente no encontrado")));
        registrosmedico.setFecha(dto.getFecha());
        registrosmedico.setDescripcion(dto.getDescripcion());
        registrosmedico.setEstado(dto.getEstado());
        return registrosmedico;
    }
}


