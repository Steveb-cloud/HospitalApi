package com.hospitala.hospitalapi.repositories;

import com.hospitala.hospitalapi.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    // Puedes añadir métodos de consulta personalizados si es necesario
}

