package br.edu.imepac.comum.repositories;

import br.edu.imepac.comum.models.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConsultaRepository extends JpaRepository<Consulta, Long> {
}


