package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

	Endereco findByAlojamentoId(Integer idAlojamento);
}
