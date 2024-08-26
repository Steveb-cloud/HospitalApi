package com.hospitala.hospitalapi.service;

import com.hospitala.hospitalapi.DTO.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PacienteService {

    @Autowired
    private RestTemplate restTemplate;

    public String transferirPaciente(PacienteDTO pacienteDTO) {
        String url = "http://url-del-hospital-b/api/pacientes/recibir"; // URL de la API de Hospital B

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<PacienteDTO> requestEntity = new HttpEntity<>(pacienteDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return "Paciente transferido exitosamente";
        } else {
            return "Error al transferir el paciente";
        }
    }
}
