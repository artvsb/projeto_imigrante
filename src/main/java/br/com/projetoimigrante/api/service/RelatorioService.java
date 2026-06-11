package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.dto.*;
import br.com.projetoimigrante.api.repository.FamiliaAlojamentoRepository;
import br.com.projetoimigrante.api.repository.RefugiadosPorPaisViewDTO;
import br.com.projetoimigrante.api.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

	@Autowired
	private RelatorioRepository relatorioRepository;

	@Autowired
	private FamiliaAlojamentoRepository familiaAlojamentoRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private record CampoPermitido(
			String tabela,
			String campo,
			String expressaoSql,
			String rotulo
	) {}

	private static final List<CampoPermitido> CAMPOS_PERMITIDOS_IMIGRANTE = List.of(
			new CampoPermitido("imigrante", "nome", "i.nome", "Nome do imigrante"),
			new CampoPermitido("imigrante", "data_nascimento", "i.data_nascimento", "Data de nascimento"),
			new CampoPermitido("imigrante", "sexo", "i.sexo", "Sexo"),
			new CampoPermitido("imigrante", "nr_documento", "i.nr_documento", "Numero do documento"),
			new CampoPermitido("imigrante", "refugiado", "i.refugiado", "Refugiado"),
			new CampoPermitido("pais", "nome", "p.nome", "País de origem"),
			new CampoPermitido("familia", "nome_referencia", "f.nome_referencia", "Família"),
			new CampoPermitido("familia", "data_cadastro", "f.data_cadastro", "Data de Cadastro"),
			new CampoPermitido("familia", "status", "f.status", "Status da família"),
			new CampoPermitido("endereco", "logradouro", "e.logradouro", "Logradouro"),
			new CampoPermitido("endereco", "cidade", "e.cidade", "Cidade"),
			new CampoPermitido("endereco", "estado", "e.estado", "Estado")
	);

	private static final List<CampoPermitido> CAMPOS_PERMITIDOS_PROPRIETARIO = List.of(
			new CampoPermitido("proprietario", "nome", "pr.nome", "Nome do proprietário"),
			new CampoPermitido("proprietario", "tipo_proprietario", "pr.tipo_proprietario", "Tipo do proprietário"),
			new CampoPermitido("proprietario", "cpf", "pr.cpf", "CPF do proprietário"),
			new CampoPermitido("proprietario", "cnpj", "pr.cnpj", "CNPJ do proprietário"),
			new CampoPermitido("proprietario", "telefone", "pr.telefone", "Telefone do proprietário"),
			new CampoPermitido("proprietario", "email", "pr.email", "Email do proprietário"),
			new CampoPermitido("alojamento", "id", "a.id", "ID do alojamento"),
			new CampoPermitido("alojamento", "tamanho_m2", "a.tamanho_m2", "Tamanho"),
			new CampoPermitido("alojamento", "custo_mensal", "a.custo_mensal", "Custo mensal"),
			new CampoPermitido("alojamento", "status", "a.status", "Status do alojamento"),
			new CampoPermitido("endereco", "logradouro", "e.logradouro", "Logradouro"),
			new CampoPermitido("endereco", "cidade", "e.cidade", "Cidade"),
			new CampoPermitido("endereco", "estado", "e.estado", "Estado"),
			new CampoPermitido("endereco", "cep", "e.cep", "CEP")
	);

	public List<OrigemPorEstadoViewDTO> buscarOrigemPorEstado() {
		return relatorioRepository.buscarOrigemPorEstado();
	}

	public List<RefugiadosPorPaisViewDTO> buscarRefugiadosPorPais() {
		return relatorioRepository.buscarRefugiadosPorPais();
	}

	public List<ImigranteResumoViewDTO> buscarResumoImigrantes() {
		return relatorioRepository.buscarResumoImigrantes();
	}

	public List<QtdImigrantesPorRegiaoViewDTO> buscarQtdImigrantesPorRegiao() {
		return relatorioRepository.buscarQtdImigrantesPorRegiao();
	}

	public List<CustoImigrantePaisViewDTO> buscarCustoImigrantePais() {
		return relatorioRepository.buscarCustoImigrantePais();
	}

	public List<FamiliaAlojamentoResponseDTO> buscarAlojamentosOcupados() {
		List<Object[]> linhas = familiaAlojamentoRepository.buscarAlojamentosOcupados();

		Map<String, FamiliaAlojamentoResponseDTO> mapa = new LinkedHashMap<>();

		for (Object[] linha : linhas) {
			Integer idAlojamento = ((Number) linha[0]).intValue();
			Integer idFamilia = ((Number) linha[1]).intValue();

			String chave = idAlojamento + "-" + idFamilia;

			FamiliaAlojamentoResponseDTO dto = mapa.computeIfAbsent(chave, k ->
					new FamiliaAlojamentoResponseDTO(
							(String) linha[2], // logradouro
							(String) linha[3], // cidade
							(String) linha[4], // estado
							(String) linha[5], // cep
							(String) linha[6], // nome familia
							new ArrayList<>()
					)
			);

			dto.membros().add((String) linha[7]);
		}

		return new ArrayList<>(mapa.values());
	}

	public List<CampoRelatorioDTO> listarCamposRelatorioImigrante() {
		return CAMPOS_PERMITIDOS_IMIGRANTE.stream()
				.map(c -> new CampoRelatorioDTO(c.tabela(), c.campo(), c.rotulo()))
				.toList();
	}

	public List<CampoRelatorioDTO> listarCamposRelatorioProprietario() {
		return CAMPOS_PERMITIDOS_PROPRIETARIO.stream()
				.map(c -> new CampoRelatorioDTO(c.tabela(), c.campo(), c.rotulo()))
				.toList();
	}

	private List<CampoPermitido> validarCamposImigrante(List<CampoSelecionadoRelatorioDTO> campos) {
		if (campos == null || campos.isEmpty()) {
			throw new RuntimeException("Informe ao menos um campo.");
		}

		List<CampoPermitido> selecionados = new ArrayList<>();

		for (CampoSelecionadoRelatorioDTO campo : campos) {
			CampoPermitido encontrado = CAMPOS_PERMITIDOS_IMIGRANTE.stream()
					.filter(c -> c.tabela().equals(campo.tabela()))
					.filter(c -> c.campo().equals(campo.campo()))
					.findFirst()
					.orElseThrow(() -> new RuntimeException("Campo não permitido."));

			selecionados.add(encontrado);
		}

		return selecionados;
	}

	private List<CampoPermitido> validarCamposProprietario(List<CampoSelecionadoRelatorioDTO> campos) {
		if (campos == null || campos.isEmpty()) {
			throw new RuntimeException("Informe ao menos um campo.");
		}

		List<CampoPermitido> selecionados = new ArrayList<>();

		for (CampoSelecionadoRelatorioDTO campo : campos) {
			CampoPermitido encontrado = CAMPOS_PERMITIDOS_PROPRIETARIO.stream()
					.filter(c -> c.tabela().equals(campo.tabela()))
					.filter(c -> c.campo().equals(campo.campo()))
					.findFirst()
					.orElseThrow(() -> new RuntimeException("Campo não permitido."));

			selecionados.add(encontrado);
		}

		return selecionados;
	}

	public RelatorioPersonalizadoResponseDTO gerarRelatorioPersonalizadoImigrante(
			RelatorioPersonalizadoRequestDTO request) {

		List<CampoPermitido> camposSelecionados = validarCamposImigrante(request.campos());

		String select = camposSelecionados.stream()
				.map(c -> c.expressaoSql() + " AS " + c.tabela() + "_" + c.campo())
				.collect(Collectors.joining(", "));

		String sql = """
        SELECT %s
        FROM imigrante i
        LEFT JOIN pais p ON p.id = i.id_pais
        LEFT JOIN familia f ON f.id = i.id_familia
        LEFT JOIN familia_alojamento fa ON fa.id_familia = f.id
        LEFT JOIN alojamento a ON a.id = fa.id_alojamento
        LEFT JOIN endereco e ON e.id_alojamento = a.id
        ORDER BY i.id
        LIMIT ?
    """.formatted(select);

		List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, request.limite());

		return new RelatorioPersonalizadoResponseDTO(
				camposSelecionados.stream()
						.map(c -> new CampoRelatorioDTO(c.tabela(), c.campo(), c.rotulo()))
						.toList(),
				linhas
		);
	}


	public RelatorioPersonalizadoResponseDTO gerarRelatorioPersonalizadoProprietario(RelatorioPersonalizadoRequestDTO request) {

		List<CampoPermitido> camposSelecionados = validarCamposProprietario(request.campos());

		String select = camposSelecionados.stream()
				.map(c -> c.expressaoSql() + " AS " + c.tabela() + "_" + c.campo())
				.collect(Collectors.joining(", "));

		String sql = """
				SELECT %s
				FROM proprietario pr
				LEFT JOIN alojamento a ON a.id_proprietario = pr.id
				LEFT JOIN endereco e ON e.id_alojamento = a.id
				ORDER BY pr.id
				LIMIT ?
				""".formatted(select);

		List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, request.limite());

		return new RelatorioPersonalizadoResponseDTO(
				camposSelecionados.stream()
						.map(c -> new CampoRelatorioDTO(c.tabela(), c.campo(), c.rotulo()))
						.toList(),
				linhas
		);
	}

	private String formatarValorCsv(String valor) {
		String valorFormatado = valor.replace("\"", "\"\"");

		if (valorFormatado.contains(";")
				|| valorFormatado.contains("\"")
				|| valorFormatado.contains("\n")) {
			return "\"" + valorFormatado + "\"";
		}

		return valorFormatado;
	}

	private String gerarCsv(RelatorioPersonalizadoResponseDTO relatorio) {
		List<String> cabecalhos = relatorio.campos().stream()
				.map(CampoRelatorioDTO::rotulo)
				.toList();

		List<List<Object>> linhas = relatorio.linhas().stream()
				.map(linha -> relatorio.campos().stream()
						.map(campo -> campo.tabela() + "_" + campo.campo())
						.map(linha::get)
						.toList())
				.toList();

		return gerarCsv(cabecalhos, linhas);
	}

	private String gerarCsv(List<String> cabecalhos, List<List<Object>> linhas) {
		StringBuilder csv = new StringBuilder();

		String cabecalho = cabecalhos.stream()
				.map(this::formatarValorCsv)
				.collect(Collectors.joining(";"));

		csv.append(cabecalho).append("\n");

		for (List<Object> linha : linhas) {
			String valores = linha.stream()
					.map(valor -> valor == null ? "" : valor.toString())
					.map(this::formatarValorCsv)
					.collect(Collectors.joining(";"));

			csv.append(valores).append("\n");
		}

		return csv.toString();
	}

	public String exportarRelatorioImigranteCsv(RelatorioPersonalizadoRequestDTO request) {
		RelatorioPersonalizadoResponseDTO relatorio =
				gerarRelatorioPersonalizadoImigrante(request);

		return gerarCsv(relatorio);
	}

	public String exportarRelatorioProprietarioCsv(RelatorioPersonalizadoRequestDTO request) {
		RelatorioPersonalizadoResponseDTO relatorio =
				gerarRelatorioPersonalizadoProprietario(request);

		return gerarCsv(relatorio);
	}

	public String exportarOrigemPorEstadoCsv() {
		List<OrigemPorEstadoViewDTO> dados = buscarOrigemPorEstado();

		return gerarCsv(
				List.of("Estado", "País", "Quantidade de imigrantes"),
				dados.stream()
						.map(item -> List.of(
								(Object) item.getEstado(),
								item.getPais(),
								item.getQtdImigrantes()
						))
						.toList()
		);
	}

	public String exportarCustoImigrantePaisCsv() {
		List<CustoImigrantePaisViewDTO> dados = buscarCustoImigrantePais();

		return gerarCsv(
				List.of("ID", "País", "Quantidade de imigrantes", "Custo/Imigrante por País"),
				dados.stream()
						.map(item -> List.of(
								(Object) item.getIdPais(),
								item.getNomePais(),
								item.getQtdImigrantes(),
								item.getCustoMedioPessoa()
						))
						.toList()
		);
	}

	public String exportarResumoAlojamentosCsv() {
		List<CustoImigrantePaisViewDTO> dados = buscarCustoImigrantePais();

		return gerarCsv(
				List.of("ID", "País", "Quantidade de imigrantes", "Custo/Imigrante por País"),
				dados.stream()
						.map(item -> List.of(
								(Object) item.getIdPais(),
								item.getNomePais(),
								item.getQtdImigrantes(),
								item.getCustoMedioPessoa()
						))
						.toList()
		);
	}

	public String exportarResumoImigrantesCsv() {
		List<ImigranteResumoViewDTO> dados = buscarResumoImigrantes();

		return gerarCsv(
				List.of("Nome", "Data de nascimento", "País de origem"),
				dados.stream()
						.map(item -> List.of(
								(Object) item.getNome(),
								item.getDataNascimento(),
								item.getPaisOrigem()
						))
						.toList()
		);
	}

	public List<AlojamentoDisponivelViewDTO> buscarAlojamentosDisponiveis() {
		return relatorioRepository.buscarAlojamentosDisponiveis();
	}

	public String alojamentosDisponiveisCsv() {
		List<AlojamentoDisponivelViewDTO> dados = buscarAlojamentosDisponiveis();

		return gerarCsv(
				List.of("ID", "Tamanho em m2", "Custo Mensal", "Status", "Logradouro",
						"Cidade", "Estado", "CEP", "ID do Proprietário", "Telefone", "E-mail"),
				dados.stream()
						.map(item -> List.of(
								(Object) item.getIdAlojamento(),
								item.getTamanhoM2(),
								item.getCustoMensal(),
								item.getStatus(),
								item.getLogradouro(),
								item.getCidade(),
								item.getEstado(),
								item.getIdProprietario(),
								item.getNomeProprietario(),
								item.getTelefoneProprietario(),
								item.getEmailProprietario()
						))
						.toList()
		);
	}
}
