package br.com.projetoimigrante.api.controller;

import br.com.projetoimigrante.api.dto.CustoImigrantePaisViewDTO;
import br.com.projetoimigrante.api.dto.ImigranteResumoViewDTO;
import br.com.projetoimigrante.api.dto.OrigemPorEstadoViewDTO;
import br.com.projetoimigrante.api.dto.QtdImigrantesPorRegiaoViewDTO;
import br.com.projetoimigrante.api.repository.RefugiadosPorPaisViewDTO;
import br.com.projetoimigrante.api.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/custo-por-imigrante-por-pais")
	@ResponseStatus(HttpStatus.OK)
	public List<CustoImigrantePaisViewDTO> buscarCustoImigrantePais() {
		return relatorioService.buscarCustoImigrantePais();
	}

}