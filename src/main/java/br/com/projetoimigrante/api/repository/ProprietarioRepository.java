package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Integer> {
}
