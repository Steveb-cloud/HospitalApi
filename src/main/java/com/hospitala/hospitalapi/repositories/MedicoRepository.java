package com.hospitala.hospitalapi.repositories;

import com.hospitala.hospitalapi.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    // Puedes añadir métodos de consulta personalizados si es necesario
}
