package br.edu.imepac.repositories;

import br.edu.imepac.models.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProtuarioRepository extends JpaRepository<Prontuario, Long> {
}
