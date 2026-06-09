package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.FamiliaAlojamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamiliaAlojamentoRepository extends JpaRepository<FamiliaAlojamento, Integer> {

	@Procedure(procedureName = "alocar_familia")
	void alocarFamilia(
			@Param("p_id_familia") Integer idFamilia,
			@Param("p_id_alojamento") Integer idAlojamento
	);

	@Procedure(procedureName = "desalocar_familia")
	void desalocarFamilia(
			@Param("p_id_familia") Integer idFamilia
	);
}