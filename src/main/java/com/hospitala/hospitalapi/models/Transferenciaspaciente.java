package com.hospitala.hospitalapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "transferenciaspacientes")
public class Transferenciaspaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "hospital_origen")
    private String hospitalOrigen;

    @Column(name = "hospital_destino")
    private String hospitalDestino;

    @Column(name = "fecha_solicitud", nullable = false)
    private Instant fechaSolicitud;

    @ColumnDefault("'en proceso'")
    @Column(name = "estado", length = 20)
    private String estado;

}