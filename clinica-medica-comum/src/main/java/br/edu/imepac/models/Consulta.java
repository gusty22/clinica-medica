package br.edu.imepac.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "LocalDataTime", nullable = false, length = 100)
    private LocalDateTime dataHorario;

    private String sintomas;

    private boolean eRetorno;

    private boolean estaAtiva;

    @ManyToOne
    @JoinColumn(name = "conveniio", nullable = false)
    private Convenio convenio;

}
