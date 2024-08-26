package com.hospitala.hospitalapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "medicos")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "email", length = 100)
    private String email;

    @ColumnDefault("'activo'")
    @Column(name = "estado", length = 20)
    private String estado;

    @OneToMany(mappedBy = "medico")
    private Set<Cita> citas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "medico")
    private Set<Notificacione> notificaciones = new LinkedHashSet<>();

    public Medico(Integer medicoId) {
    }

    public Medico() {

    }
}