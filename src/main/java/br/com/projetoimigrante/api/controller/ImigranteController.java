package br.com.projetoimigrante.api.controller;

import br.com.projetoimigrante.api.model.Imigrante;
import br.com.projetoimigrante.api.service.ImigranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/imigrantes")
public class ImigranteController {

	@Autowired
	private ImigranteService imigranteService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void criarImigrante(@RequestBody Imigrante imigrante) {
		imigranteService.criarImigrante(imigrante);
	}

	@GetMapping("/{documento}")
	@ResponseStatus(HttpStatus.OK)
	public Imigrante buscarPorDocumento(@PathVariable String documento) {
		return imigranteService.buscarPorDocumento(documento);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Imigrante> buscarTodosImigrantes() { return imigranteService.buscarTodosImigrantes(); }

	@DeleteMapping("/{documento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarPorDocumento(@PathVariable String documento) {
		imigranteService.deletarPorDocumento(documento);
	}

	@PutMapping("/{documento}")
	public Imigrante atualizarPorDocumento(
			@PathVariable String documento,
			@RequestBody Imigrante imigranteAtt) {
		return imigranteService.atualizarPorDocumento(documento, imigranteAtt);
	}



}
