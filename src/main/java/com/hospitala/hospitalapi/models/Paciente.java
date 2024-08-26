package com.hospitala.hospitalapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @ColumnDefault("'activo'")
    @Column(name = "estado", length = 20)
    private String estado;

    @OneToMany(mappedBy = "paciente")
    private Set<Cita> citas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "paciente")
    private Set<Notificacione> notificaciones = new LinkedHashSet<>();

    @OneToMany(mappedBy = "paciente")
    private Set<Registrosmedico> registrosmedicos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "paciente")
    private Set<Transferenciaspaciente> transferenciaspacientes = new LinkedHashSet<>();

    public Paciente(Integer pacienteId) {
    }

    public Paciente() {

    }
}