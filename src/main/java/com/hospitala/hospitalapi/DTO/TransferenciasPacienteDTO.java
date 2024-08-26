package com.hospitala.hospitalapi.DTO;

import java.time.Instant;

public class TransferenciasPacienteDTO {

    private Integer id;
    private Integer pacienteId; // Se usa Integer para manejar el ID del Paciente.
    private String hospitalOrigen;
    private String hospitalDestino;
    private Instant fechaSolicitud;
    private String estado;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getHospitalOrigen() {
        return hospitalOrigen;
    }

    public void setHospitalOrigen(String hospitalOrigen) {
        this.hospitalOrigen = hospitalOrigen;
    }

    public String getHospitalDestino() {
        return hospitalDestino;
    }

    public void setHospitalDestino(String hospitalDestino) {
        this.hospitalDestino = hospitalDestino;
    }

    public Instant getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Instant fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}


