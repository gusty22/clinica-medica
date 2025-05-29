package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "receituario", length = 1000)
    private String receituario;

    @Column(name = "exames", length = 1000)
    private String exames;

    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    @OneToOne(mappedBy = "prontuario")
    private Consulta consulta;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}
