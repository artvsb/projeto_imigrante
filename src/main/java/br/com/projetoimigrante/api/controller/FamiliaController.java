package br.com.projetoimigrante.api.controller;

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
	public List<Familia> buscarTodasFamilias() {
		return familiaService.buscarTodasFamilias();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Familia buscarFamiliaPorId(@PathVariable Long id) {
		return familiaService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarFamiliaPorId(@PathVariable Long id) {
		familiaService.deletarPorId(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarFamiliaPorId(@PathVariable Long id, @RequestBody Familia familiaAtt) {
		familiaService.atualizarPorId(id, familiaAtt);
	}

}
