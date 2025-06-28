package br.edu.imepac.comum.dtos.consulta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaRequest {
    private LocalDate data;
    private LocalTime horario;
    private Boolean eRetorno;
    private Boolean estaAtiva;
    private String sintomas;
    private Long funcionarioId;
    private Long pacienteId;
    private Long convenioId;
    private Long prontuarioId;
}
