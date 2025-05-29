package br.edu.imepac.comum.repositories;

import br.edu.imepac.comum.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
}
