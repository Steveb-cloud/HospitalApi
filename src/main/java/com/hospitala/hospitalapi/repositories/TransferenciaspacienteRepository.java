package com.hospitala.hospitalapi.repositories;

import com.hospitala.hospitalapi.models.Transferenciaspaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaspacienteRepository extends JpaRepository<Transferenciaspaciente, Integer> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
