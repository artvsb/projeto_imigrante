package br.com.projetoimigrante.api.controller;

import br.com.projetoimigrante.api.dto.*;
import br.com.projetoimigrante.api.repository.RefugiadosPorPaisViewDTO;
import br.com.projetoimigrante.api.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

	@Autowired
	private RelatorioService relatorioService;

	@GetMapping("/origem-por-estado")
	public List<OrigemPorEstadoViewDTO> buscarOrigemPorEstado() {
		return relatorioService.buscarOrigemPorEstado();
	}

	@GetMapping("/refugiados-por-pais")
	public List<RefugiadosPorPaisViewDTO> buscarRefugiadosPorPais() {
		return relatorioService.buscarRefugiadosPorPais();
	}

	@GetMapping("/resumo")
	@ResponseStatus(HttpStatus.OK)
	public List<ImigranteResumoViewDTO> buscarResumoImigrantes() {
		return relatorioService.buscarResumoImigrantes();
	}

	@GetMapping("/qtd-por-regiao")
	@ResponseStatus(HttpStatus.OK)
	public List<QtdImigrantesPorRegiaoViewDTO> buscarQtdImigrantesPorRegiao() {
		return relatorioService.buscarQtdImigrantesPorRegiao();
	}

	@GetMapping("/custo-imigrante-pais")
	@ResponseStatus(HttpStatus.OK)
	public List<CustoImigrantePaisViewDTO> buscarCustoImigrantePais() {
		return relatorioService.buscarCustoImigrantePais();
	}

	@GetMapping("/personalizado/camposImigrante")
	@ResponseStatus(HttpStatus.OK)
	public List<CampoRelatorioDTO> listasCamposRelatorioImigrante() {
		return relatorioService.listarCamposRelatorioImigrante();
	}

	@GetMapping("/personalizado/camposProprietario")
	@ResponseStatus(HttpStatus.OK)
	public List<CampoRelatorioDTO> listasCamposRelatorioProprietario() {
		return relatorioService.listarCamposRelatorioProprietario();
	}

	@PostMapping("/personalizadoImigrante")
	@ResponseStatus(HttpStatus.CREATED)
	public RelatorioPersonalizadoResponseDTO gerarRelatorioPersonalizadoImigrante(
			@RequestBody RelatorioPersonalizadoRequestDTO request) {
		return relatorioService.gerarRelatorioPersonalizadoImigrante(request);
	}

	@PostMapping("/personalizadoProprietario")
	@ResponseStatus(HttpStatus.CREATED)
	public RelatorioPersonalizadoResponseDTO gerarRelatorioPersonalizadoProprietario(
			@RequestBody RelatorioPersonalizadoRequestDTO request) {
		return relatorioService.gerarRelatorioPersonalizadoProprietario(request);
	}

	@PostMapping(value = "/personalizadoImigrante/csv", produces = "text/csv")
	public ResponseEntity<String> exportarRelatorioImigranteCsv(
			@RequestBody RelatorioPersonalizadoRequestDTO request) {

		String csv = relatorioService.exportarRelatorioImigranteCsv(request);

		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=relatorio-imigrantes.csv")
				.body(csv);
	}

	@PostMapping(value = "/personalizadoProprietario/csv", produces = "text/csv")
	public ResponseEntity<String> exportarRelatorioProprietarioCsv(
			@RequestBody RelatorioPersonalizadoRequestDTO request) {

		String csv = relatorioService.exportarRelatorioProprietarioCsv(request);

		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=relatorio-proprietarios.csv")
				.body(csv);
	}

	@GetMapping(value = "/origem-por-estado/csv", produces = "text/csv")
	public ResponseEntity<String> exportarOrigemPorEstadoCsv() {
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=origem-por-estado.csv")
				.body(relatorioService.exportarOrigemPorEstadoCsv());
	}

	@GetMapping(value = "/custo-imigrante-pais/csv", produces = "text/csv")
	public ResponseEntity<String> exportarCustoImigrantePaisCsv() {
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=origem-por-estado.csv")
				.body(relatorioService.exportarCustoImigrantePaisCsv());
	}

	@GetMapping(value = "/resumo/csv", produces = "text/csv")
	public ResponseEntity<String> exportarResumoImigrantesCsv() {
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=resumo-imigrantes.csv")
				.body(relatorioService.exportarResumoImigrantesCsv());
	}

	@GetMapping("/alojamentos-disponiveis")
	public List<AlojamentoDisponivelViewDTO> buscarAlojamentosDisponiveis() {
		return relatorioService.buscarAlojamentosDisponiveis();
	}

	@GetMapping(value = "/alojamentos-disponiveis/csv", produces = "text/csv")
	public ResponseEntity<String> buscarAlojamentosDisponiveisCsv() {
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=resumo-imigrantes.csv")
				.body(relatorioService.alojamentosDisponiveisCsv());
	}

}