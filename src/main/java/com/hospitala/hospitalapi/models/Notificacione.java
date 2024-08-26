package com.hospitala.hospitalapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notificaciones")
public class Notificacione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @Lob
    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "fecha_envio", nullable = false)
    private Instant fechaEnvio;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @ColumnDefault("'enviada'")
    @Column(name = "estado", length = 20)
    private String estado;

    public Notificacione(int id, Integer pacienteId, Integer medicoId, String mensaje, LocalDateTime fechaEnvio, String tipo, String estado) {

    }

    public Notificacione() {

    }

    public void setFechaEnvio(Instant fechaEnvio) {
    }

    public Integer getPacienteId() {
        return null;
    }

}