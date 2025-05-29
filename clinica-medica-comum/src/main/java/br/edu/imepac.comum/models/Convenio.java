package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @OneToMany(mappedBy = "convenio")
    private List<Consulta> consultas;
}
