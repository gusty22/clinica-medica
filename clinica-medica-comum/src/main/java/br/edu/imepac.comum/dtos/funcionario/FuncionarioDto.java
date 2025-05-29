package br.edu.imepac.comum.dtos.funcionario;

import br.edu.imepac.comum.dtos.especialidade.EspecialidadeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDto {
    private Long id;
    private String usuario;
    private String senha;
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
    private List<EspecialidadeDto> especialidades;
}