package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.FamiliaAlojamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamiliaAlojamentoRepository extends JpaRepository<FamiliaAlojamento, Long> {
}
