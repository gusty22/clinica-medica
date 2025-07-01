package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "prontuarios")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receituario", length = 1000)
    private String receituario;

    @Column(name = "exames", length = 1000)
    private String exames;

    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    @OneToMany(mappedBy = "prontuario")
    private List<Consulta> consultas;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}
