package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.Imigrante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImigranteRepository extends JpaRepository<Imigrante, Long> {

	Optional<Imigrante> findByNrPassaporte(String nrPassaporte);

	Optional<Imigrante> findByRnm(String rnm);
}
