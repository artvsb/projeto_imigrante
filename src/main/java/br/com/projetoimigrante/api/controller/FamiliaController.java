package br.com.projetoimigrante.api.controller;

import br.com.projetoimigrante.api.dto.FamiliaResponseDTO;
import br.com.projetoimigrante.api.model.Familia;
import br.com.projetoimigrante.api.service.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/familias")
public class FamiliaController {

	@Autowired
	private FamiliaService familiaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Familia criarFamilia(@RequestBody Familia familia) {
		return familiaService.criarFamilia(familia);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<FamiliaResponseDTO> buscarTodasFamilias() {
		return familiaService.buscarTodasFamilias();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Familia buscarFamiliaPorId(@PathVariable Integer id) {
		return familiaService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarFamiliaPorId(@PathVariable Integer id) {
		familiaService.deletarPorId(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarFamiliaPorId(@PathVariable Integer id, @RequestBody Familia familiaAtt) {
		familiaService.atualizarPorId(id, familiaAtt);
	}

}
