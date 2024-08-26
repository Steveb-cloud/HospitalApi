package com.hospitala.hospitalapi.controllers;

import com.hospitala.hospitalapi.DTO.CitaDTO;
import com.hospitala.hospitalapi.models.Cita;
import com.hospitala.hospitalapi.models.Medico;
import com.hospitala.hospitalapi.models.Paciente;
import com.hospitala.hospitalapi.repositories.CitaRepository;
import com.hospitala.hospitalapi.repositories.MedicoRepository;
import com.hospitala.hospitalapi.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/citas")
public class CitasController {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // GET: api/citas
    @GetMapping
    public List<CitaDTO> getCitas() {
        List<Cita> citas = citaRepository.findAll();
        return citas.stream()
                .map(this::convierteA_dto)
                .collect(Collectors.toList());
    }

    // GET: api/citas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> getCita(@PathVariable Integer id) {
        Optional<Cita> cita = citaRepository.findById(id);

        if (cita.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(convierteA_dto(cita.get()), HttpStatus.OK);
    }

    // PUT: api/citas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> putCita(@PathVariable Integer id, @RequestBody CitaDTO citaDTO) {
        if (!id.equals(citaDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!citaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Cita cita = transformaDTOaCita(citaDTO);
        citaRepository.save(cita);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // POST: api/citas
    @PostMapping
    public ResponseEntity<CitaDTO> postCita(@RequestBody CitaDTO citaDTO) {
        Cita cita = transformaDTOaCita(citaDTO);
        cita = citaRepository.save(cita);

        return ResponseEntity
                .created(URI.create("/api/citas/" + cita.getId()))
                .body(convierteA_dto(cita));
    }

    // DELETE: api/citas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Integer id) {
        if (!citaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        citaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CitaDTO convierteA_dto(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());
        dto.setPacienteId(cita.getPaciente() != null ? cita.getPaciente().getId() : null);
        dto.setMedicoId(cita.getMedico() != null ? cita.getMedico().getId() : null);
        dto.setFecha(cita.getFecha());
        dto.setMotivo(cita.getMotivo());
        dto.setEstado(cita.getEstado());
        return dto;
    }

    private Cita transformaDTOaCita(CitaDTO citaDTO) {
        Cita cita = new Cita();
        cita.setId(citaDTO.getId());

        // Fetch existing entities or handle cases where they don't exist
        if (citaDTO.getPacienteId() != null) {
            Optional<Paciente> pacienteOptional = pacienteRepository.findById(citaDTO.getPacienteId());
            cita.setPaciente(pacienteOptional.orElse(null));
        }

        if (citaDTO.getMedicoId() != null) {
            Optional<Medico> medicoOptional = medicoRepository.findById(citaDTO.getMedicoId());
            cita.setMedico(medicoOptional.orElse(null));
        }

        cita.setFecha(citaDTO.getFecha());
        cita.setMotivo(citaDTO.getMotivo());
        cita.setEstado(citaDTO.getEstado());
        return cita;
    }
}



