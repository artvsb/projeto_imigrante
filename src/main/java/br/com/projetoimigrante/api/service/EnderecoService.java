package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.model.Endereco;
import br.com.projetoimigrante.api.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public void criarEndereco(Integer idAlojamento, Endereco endereco) {
		enderecoRepository.save(endereco);
	}

	public List<Endereco> buscarTodosEnderecos() {
		return enderecoRepository.findAll();
	}

	public Endereco buscarPorAlojamento(Integer idAlojamento) {
		return enderecoRepository.findByAlojamentoId(idAlojamento);
	}

	public void deletarPorId(Integer id) {
		enderecoRepository.deleteById(id);
	}

	public void atualizarPorAlojamento(Integer idAlojamento, Endereco enderecoAtt) {
		Endereco endereco0 = enderecoRepository.findByAlojamentoId(idAlojamento);

		endereco0.setLogradouro(enderecoAtt.getLogradouro());
		endereco0.setCep(enderecoAtt.getCep());
		enderecoRepository.save(endereco0);
	}
}
