package com.hospitala.hospitalapi.controllers;

import com.hospitala.hospitalapi.DTO.MedicoDTO;
import com.hospitala.hospitalapi.models.Medico;
import com.hospitala.hospitalapi.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController { // Renombrado a MedicoController

    @Autowired
    private MedicoRepository medicoRepository;

    // GET: api/medicos
    @GetMapping
    public List<MedicoDTO> getMedicos() {
        return medicoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET: api/medicos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> getMedico(@PathVariable int id) {
        Optional<Medico> medico = medicoRepository.findById(id);

        return medico.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: api/medicos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> putMedico(@PathVariable int id, @RequestBody MedicoDTO medicoDTO) {
        if (id != medicoDTO.getId()) {
            return ResponseEntity.badRequest().build();
        }

        if (!medicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Medico medico = convertToEntity(medicoDTO);
        medicoRepository.save(medico);
        return ResponseEntity.noContent().build();
    }

    // POST: api/medicos
    @PostMapping
    public ResponseEntity<MedicoDTO> postMedico(@RequestBody MedicoDTO medicoDTO) {
        Medico medico = convertToEntity(medicoDTO);
        medico = medicoRepository.save(medico);
        return ResponseEntity.created(URI.create("/api/medicos/" + medico.getId())).body(convertToDTO(medico));
    }

    // DELETE: api/medicos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable int id) {
        if (!medicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        medicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private MedicoDTO convertToDTO(Medico medico) {
        return new MedicoDTO(
                medico.getId(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getEspecialidad(),
                medico.getTelefono(),
                medico.getEmail(),
                medico.getEstado()
        );
    }

    private Medico convertToEntity(MedicoDTO medicoDTO) {
        Medico medico = new Medico();
        medico.setId(medicoDTO.getId());
        medico.setNombre(medicoDTO.getNombre());
        medico.setApellido(medicoDTO.getApellido());
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        medico.setTelefono(medicoDTO.getTelefono());
        medico.setEmail(medicoDTO.getEmail());
        medico.setEstado(medicoDTO.getEstado());
        return medico;
    }
}

