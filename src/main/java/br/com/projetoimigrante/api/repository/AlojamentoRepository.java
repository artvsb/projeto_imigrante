package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.Alojamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlojamentoRepository extends JpaRepository<Alojamento, Long> {
}
