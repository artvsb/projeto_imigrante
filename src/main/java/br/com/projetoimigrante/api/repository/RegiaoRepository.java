package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Integer> {
}
