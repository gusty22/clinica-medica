package br.edu.imepac.comum.domain;

import java.time.LocalDateTime;

public class ConsultaDomain {
    private Long id;
    private LocalDateTime dataHorario;
    private String sintomas;
    private boolean eRetorno;
    private boolean estaAtiva;

    public ConsultaDomain(Long id, LocalDateTime dataHorario, String sintomas, boolean eRetorno, boolean estaAtiva) {
        this.id = id;
        this.dataHorario = dataHorario;
        this.sintomas = sintomas;
        this.eRetorno = eRetorno;
        this.estaAtiva = estaAtiva;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHorario() {
        return dataHorario;
    }

    public String getSintomas() {
        return sintomas;
    }

    public boolean isERetorno() {
        return eRetorno;
    }

    public boolean isEstaAtiva() {
        return estaAtiva;
    }

    public void setDataHorario(LocalDateTime dataHorario) {
        if (dataHorario == null) {
            System.out.println("Data e horário obrigatório");
        }
        this.dataHorario = dataHorario;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public void setERetorno(boolean eRetorno) {
        this.eRetorno = eRetorno;
    }

    public void setEstaAtiva(boolean estaAtiva) {
        this.estaAtiva = estaAtiva;
    }
}
