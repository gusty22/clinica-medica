package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "senha", nullable = false)
    private String senha;

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

    @OneToMany(mappedBy = "funcionario")
    private List<Consulta> consultas;

    @ManyToMany
    @JoinTable(
            name = "funcionario_especialidade",
            joinColumns = @JoinColumn(name = "funcionario_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
    private List<Especialidade> especialidades;
}

