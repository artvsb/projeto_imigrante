package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {
}
