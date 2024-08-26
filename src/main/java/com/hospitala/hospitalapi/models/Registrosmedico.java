package com.hospitala.hospitalapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "registrosmedicos")
public class Registrosmedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @ColumnDefault("'activo'")
    @Column(name = "estado", length = 20)
    private String estado;

    public Registrosmedico(int id, Integer pacienteId, LocalDateTime fecha, String descripcion, String estado) {
    }

    public Registrosmedico() {

    }

    public Integer getPacienteId() {
        return null;
    }
}