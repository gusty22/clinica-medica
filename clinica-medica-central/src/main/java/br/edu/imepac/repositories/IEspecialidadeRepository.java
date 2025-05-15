package br.edu.imepac.repositories;

import br.edu.imepac.models.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEspecialidadeRepository extends JpaRepository<Especialidade, Long> {
}
