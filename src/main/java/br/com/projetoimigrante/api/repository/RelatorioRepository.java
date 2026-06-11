package br.com.projetoimigrante.api.repository;

import br.com.projetoimigrante.api.dto.*;
import br.com.projetoimigrante.api.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Familia, Integer> {

	@Query(value = """
		SELECT 
				estado,
				nome AS pais,
				qtd_imigrantes AS qtdImigrantes
		FROM vw_origem_por_estado		
			""", nativeQuery = true)
	List<OrigemPorEstadoViewDTO> buscarOrigemPorEstado();

	@Query(value = """
			SELECT 
				id_pais AS idPais,
				pais_origem AS paisOrigem,
				total_refugiados AS totalRefugiados
			FROM vw_refugiados_por_pais	
			""", nativeQuery = true)
	List<RefugiadosPorPaisViewDTO> buscarRefugiadosPorPais();

	@Query(value = """
    SELECT
        nome,
        data_nascimento AS dataNascimento,
        pais_origem AS paisOrigem
    FROM vw_imigrantes_resumo
    """, nativeQuery = true)
	List<ImigranteResumoViewDTO> buscarResumoImigrantes();

	@Query(value = """
			SELECT 
					id_regiao AS idRegiao,
					regiao AS nomeRegiao,
					total_imigrantes AS totalImigrantes
			FROM vw_imigrantes_por_regiao		
			""", nativeQuery = true)
	List<QtdImigrantesPorRegiaoViewDTO> buscarQtdImigrantesPorRegiao();

	@Query(value = """
			SELECT
				id_pais AS idPais,
				nome_pais AS nomePais,
				qtd_imigrantes AS qtdImigrantes,
				custo_medio_pessoa AS custoMedioPessoa
			FROM vw_custo_por_imigrante_por_pais	
			""", nativeQuery = true)
	List<CustoImigrantePaisViewDTO> buscarCustoImigrantePais();

	@Query(value = """
    SELECT
        id_alojamento AS idAlojamento,
        tamanho_m2 AS tamanhoM2,
        custo_mensal AS custoMensal,
        status,
        logradouro,
        cidade,
        estado,
        cep,
        id_proprietario AS idProprietario,
        nome_proprietario AS nomeProprietario,
        telefone_proprietario AS telefoneProprietario,
        email_proprietario AS emailProprietario
    FROM vw_alojamentos_disponiveis
""", nativeQuery = true)
	List<AlojamentoDisponivelViewDTO> buscarAlojamentosDisponiveis();

}
