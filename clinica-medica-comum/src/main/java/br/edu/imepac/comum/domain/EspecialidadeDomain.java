package br.edu.imepac.comum.domain;

// EspecialidadeDomain
public class EspecialidadeDomain {
    private Long id;
    private String nome;
    private String descricao;

    public EspecialidadeDomain(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null){
            System.out.println("Nome obrigatório");
            }
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
