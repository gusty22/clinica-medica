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
@Table(name = "perfis")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(nullable = false)
    private boolean cadastrarFuncionario;

    @Column(nullable = false)
    private boolean lerFuncionario;

    @Column(nullable = false)
    private boolean atualizarFuncionario;

    @Column(nullable = false)
    private boolean deletarFuncionario;

    @Column(nullable = false)
    private boolean listarFuncionario;

    @Column(nullable = false)
    private boolean cadastrarPaciente;

    @Column(nullable = false)
    private boolean lerPaciente;

    @Column(nullable = false)
    private boolean atualizarPaciente;

    @Column(nullable = false)
    private boolean deletarPaciente;

    @Column(nullable = false)
    private boolean listarPaciente;

    @Column(nullable = false)
    private boolean cadastrarConsulta;

    @Column(nullable = false)
    private boolean lerConsulta;

    @Column(nullable = false)
    private boolean atualizarConsulta;

    @Column(nullable = false)
    private boolean deletarConsulta;

    @Column(nullable = false)
    private boolean listarConsulta;

    @Column(nullable = false)
    private boolean cadastrarEspecialidade;

    @Column(nullable = false)
    private boolean lerEspecialidade;

    @Column(nullable = false)
    private boolean atualizarEspecialidade;

    @Column(nullable = false)
    private boolean deletarEspecialidade;

    @Column(nullable = false)
    private boolean listarEspecialidade;

    @Column(nullable = false)
    private boolean cadastrarConvenio;

    @Column(nullable = false)
    private boolean lerConvenio;

    @Column(nullable = false)
    private boolean atualizarConvenio;

    @Column(nullable = false)
    private boolean deletarConvenio;

    @Column(nullable = false)
    private boolean listarConvenio;

    @Column(nullable = false)
    private boolean cadastrarProntuario;

    @Column(nullable = false)
    private boolean lerProntuario;

    @Column(nullable = false)
    private boolean atualizarProntuario;

    @Column(nullable = false)
    private boolean deletarProntuario;

    @Column(nullable = false)
    private boolean listarProntuario;

    @Column(name = "ler_actions_application", nullable = false)
    private boolean lerActionsApplication;

    @OneToMany(mappedBy = "perfil")
    private List<Funcionario> funcionarios;
}
