package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, String> {
}
