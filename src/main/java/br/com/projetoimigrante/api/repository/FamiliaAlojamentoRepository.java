package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.model.FamiliaAlojamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

	@Query(value = """
    SELECT
        a.id,
        f.id,
        e.logradouro,
        e.cidade,
        e.estado,
        e.cep,
        f.nome_referencia,
        i.nome
    FROM familia_alojamento fa
    JOIN alojamento a
        ON a.id = fa.id_alojamento
    JOIN endereco e
        ON e.id_alojamento = a.id
    JOIN familia f
        ON f.id = fa.id_familia
    JOIN imigrante i
        ON i.id_familia = f.id
    WHERE fa.data_hora_saida IS NULL
    ORDER BY a.id, f.id, i.nome
    """, nativeQuery = true)
	List<Object[]> buscarAlojamentosOcupados();
}