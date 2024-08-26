package com.hospitala.hospitalapi.repositories;

import com.hospitala.hospitalapi.models.Notificacione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacione, Integer> {
}

