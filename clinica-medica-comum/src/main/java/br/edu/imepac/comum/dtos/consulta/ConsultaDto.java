package br.edu.imepac.comum.dtos.consulta;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioDto;
import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDto {
    private Long id;
    private LocalDate data;
    private LocalTime horario;
    private FuncionarioDto funcionario;
    private PacienteDto paciente;
    private ProntuarioDto prontuario;
}
