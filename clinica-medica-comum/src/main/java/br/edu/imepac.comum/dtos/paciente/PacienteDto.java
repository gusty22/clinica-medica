package br.edu.imepac.comum.dtos.paciente;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDto {
    private Long id;
    private String nome;
    private int idade;
    private String sexo;
    private String cpf;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String contato;
    private String email;
    private LocalDate dataNascimento;
    private ConvenioDto convenio;
}