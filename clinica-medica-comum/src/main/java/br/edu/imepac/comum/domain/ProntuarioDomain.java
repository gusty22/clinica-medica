package br.edu.imepac.comum.domain;

// ProntuarioDomain
public class ProntuarioDomain {
    private Long id;
    private String receituario;
    private String exames;
    private String observacoes;

    public ProntuarioDomain(Long id, String receituario, String exames, String observacoes) {
        this.id = id;
        this.receituario = receituario;
        this.exames = exames;
        this.observacoes = observacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceituario() {
        return receituario;
    }

    public void setReceituario(String receituario) {
        this.receituario = receituario;
    }

    public String getExames() {
        return exames;
    }

    public void setExames(String exames) {
        this.exames = exames;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
