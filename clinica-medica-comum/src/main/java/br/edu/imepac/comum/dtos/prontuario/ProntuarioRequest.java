package br.edu.imepac.comum.dtos.prontuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProntuarioRequest {
    private String receituario;
    private String exames;
    private String observacoes;
    private Long pacienteId;
}