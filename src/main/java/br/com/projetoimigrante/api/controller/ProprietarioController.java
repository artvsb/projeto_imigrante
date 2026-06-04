package br.com.projetoimigrante.api.controller;

import br.com.projetoimigrante.api.model.Proprietario;
import br.com.projetoimigrante.api.service.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

	@Autowired
	private ProprietarioService proprietarioService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void criarProprietario(@RequestBody Proprietario proprietario) {
		proprietarioService.criarProprietario(proprietario);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Proprietario> buscarTpdpsProprietarios() {
		return proprietarioService.buscarTodosProprietarios();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Proprietario buscarProprietarioPorId(@PathVariable Long id) {
		return proprietarioService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id) {
		proprietarioService.deletarPorId(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPorId(@PathVariable Long id, @RequestBody Proprietario proprietarioAtt) {
		proprietarioService.atualizarPorId(id, proprietarioAtt);
	}

}
