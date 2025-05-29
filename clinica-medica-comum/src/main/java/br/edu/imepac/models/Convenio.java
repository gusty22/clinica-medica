package br.edu.imepac.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Convenio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String descricao;

    @OneToOne
    private Consulta consulta;
}
