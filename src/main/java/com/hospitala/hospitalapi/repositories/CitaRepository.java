package com.hospitala.hospitalapi.repositories;

import com.hospitala.hospitalapi.models.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
}

