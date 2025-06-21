package br.edu.imepac.comum.repositories;

import br.edu.imepac.comum.models.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConvenioRepository extends JpaRepository<Convenio, Long> {
}
