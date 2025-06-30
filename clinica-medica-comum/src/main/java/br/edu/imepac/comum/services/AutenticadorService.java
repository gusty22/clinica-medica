// clinica-medica-comum/src/main/java/br/edu/imepac/comum/services/AutenticadorService.java
package br.edu.imepac.comum.services;

import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.repositories.IFuncionarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AutenticadorService {

    private final FuncionarioService funcionarioService; // Injetado
    private final IFuncionarioRepository funcionarioRepository; // Adicione esta injeção para buscar o funcionário

    // Método para autenticar e retornar o Funcionario completo, incluindo seu Perfil
    public Optional<Funcionario> autenticar(String usuario, String senha) {
        // Em um sistema real, a senha deveria ser criptografada no banco de dados
        // e você usaria um algoritmo de hashing (ex: BCrypt) para comparar a senha fornecida
        // com a senha hasheada armazenada.
        // Por simplicidade, faremos uma busca direta.
        return funcionarioRepository.findByUsuarioAndSenha(usuario, senha);
    }
}