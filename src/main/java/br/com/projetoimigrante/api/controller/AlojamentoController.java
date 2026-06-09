package br.com.projetoimigrante.api.controller;

import br.com.projetoimigrante.api.model.Alojamento;
import br.com.projetoimigrante.api.model.Endereco;
import br.com.projetoimigrante.api.service.AlojamentoService;
import br.com.projetoimigrante.api.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alojamentos")
public class AlojamentoController {

	@Autowired
	private AlojamentoService alojamentoService;

	@Autowired
	private EnderecoService enderecoService;

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
	public Alojamento buscarPorId(@PathVariable Integer id) {
		return alojamentoService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Integer id) {
		alojamentoService.deletarPorId(id);
	}

	@PostMapping("/{idAlojamento}/endereco")
	@ResponseStatus(HttpStatus.CREATED)
	public void criarEndereco(
			@PathVariable Integer idAlojamento,
			@RequestBody Endereco endereco
	) { enderecoService.criarEndereco(idAlojamento, endereco);}

	@GetMapping("/{idAlojamento}/endereco")
	@ResponseStatus(HttpStatus.OK)
	public Endereco buscarEnderecoPorAlojamento(@PathVariable Integer idAlojamento) {
		return enderecoService.buscarPorAlojamento(idAlojamento);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarEnderecoPorAlojamento(@PathVariable Integer idAlojamento, @RequestBody Endereco enderecoAtt) {
		enderecoService.atualizarPorAlojamento(idAlojamento, enderecoAtt);
	}
}
