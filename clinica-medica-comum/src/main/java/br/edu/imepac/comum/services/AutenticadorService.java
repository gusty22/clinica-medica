
package br.edu.imepac.comum.services;

import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.repositories.IFuncionarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AutenticadorService {

    private final FuncionarioService funcionarioService;
    private final IFuncionarioRepository funcionarioRepository;


    public Optional<Funcionario> autenticar(String usuario, String senha) {
        return funcionarioRepository.findByUsuarioAndSenha(usuario, senha);
    }
}