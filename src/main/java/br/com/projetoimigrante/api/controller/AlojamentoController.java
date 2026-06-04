package br.com.projetoimigrante.api.controller;

import br.com.projetoimigrante.api.model.Alojamento;
import br.com.projetoimigrante.api.service.AlojamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alojamentos")
public class AlojamentoController {

	@Autowired
	private AlojamentoService alojamentoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void criarAlojamento(@RequestBody Alojamento alojamento) {
		alojamentoService.criarAlojamento(alojamento);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Alojamento> buscarTodosAlojamentos() {
		return alojamentoService.buscarTodosAlojamentos();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Alojamento buscarPorId(@PathVariable Long id) {
		return alojamentoService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id) {
		alojamentoService.deletarPorId(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPorId(@PathVariable Long id, @RequestBody Alojamento alojamentoAtt) {
		alojamentoService.atualizarPorId(id, alojamentoAtt);
	}
}
