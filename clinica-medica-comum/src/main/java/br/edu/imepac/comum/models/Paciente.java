package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "idade", nullable = false)
    private int idade;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    public enum Sexo {
        MASCULINO, FEMININO
    }

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "rua")
    private String rua;

    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "contato")
    private String contato;

    @Column(name = "email")
    private String email;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;

    @ManyToOne
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;
}

