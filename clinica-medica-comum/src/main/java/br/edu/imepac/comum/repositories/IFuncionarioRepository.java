package br.edu.imepac.comum.repositories;

import br.edu.imepac.comum.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByUsuarioAndSenha(String usuario, String senha);
}
