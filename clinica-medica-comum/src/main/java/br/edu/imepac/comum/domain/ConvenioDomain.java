package br.edu.imepac.comum.domain;

public class ConvenioDomain {
    private Long id;
    private String nome;
    private String descricao;

    public ConvenioDomain(Long id, String nome, String descricao) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório");
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
        if (nome == null ){
            System.out.println("A variável 'nome' está nula");
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
