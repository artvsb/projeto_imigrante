package br.com.projetoimigrante.api.controller;

import br.com.projetoimigrante.api.dto.AlocarFamiliaRequestDTO;
import br.com.projetoimigrante.api.dto.FamiliaAlojamentoResponseDTO;
import br.com.projetoimigrante.api.model.FamiliaAlojamento;
import br.com.projetoimigrante.api.service.FamiliaAlojamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("familia_alojamento")
public class FamiliaAlojamentoController {

	@Autowired
	private FamiliaAlojamentoService familiaAlojamentoService;

	@PostMapping("/alocar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alocarFamilia(@RequestBody AlocarFamiliaRequestDTO request) {
		familiaAlojamentoService.alocarFamilia(request);
	}

	@PostMapping("/desalocar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desalocarFamilia(@RequestBody Integer idFamilia) {
		familiaAlojamentoService.desalocarFamilia(idFamilia);
	}

	@GetMapping("/alojamentos-ocupados")
	public List<FamiliaAlojamentoResponseDTO> buscarAlojamentosOcupados() {
		return familiaAlojamentoService.buscarAlojamentosOcupados();
	}
}
