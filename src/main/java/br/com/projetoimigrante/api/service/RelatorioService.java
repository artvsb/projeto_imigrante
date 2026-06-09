package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.dto.CustoImigrantePaisViewDTO;
import br.com.projetoimigrante.api.dto.ImigranteResumoViewDTO;
import br.com.projetoimigrante.api.dto.OrigemPorEstadoViewDTO;
import br.com.projetoimigrante.api.dto.QtdImigrantesPorRegiaoViewDTO;
import br.com.projetoimigrante.api.repository.RefugiadosPorPaisViewDTO;
import br.com.projetoimigrante.api.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

	@Autowired
	private RelatorioRepository relatorioRepository;

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

}
