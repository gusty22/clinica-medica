package br.edu.imepac.repositories;

import br.edu.imepac.models.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConvenioRepository extends JpaRepository<Convenio, Long> {
}
