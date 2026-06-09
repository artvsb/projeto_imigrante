package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.dto.ImigranteResumoViewDTO;
import br.com.projetoimigrante.api.dto.QtdImigrantesPorRegiaoViewDTO;
import br.com.projetoimigrante.api.model.Imigrante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImigranteRepository extends JpaRepository<Imigrante, Integer> {

	Optional<Imigrante> findByNrDocumento(String documento);


}
