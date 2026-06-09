package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.enums.TipoProprietarioEnum;
import br.com.projetoimigrante.api.model.Proprietario;
import br.com.projetoimigrante.api.repository.ProprietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProprietarioService {

	@Autowired
	private ProprietarioRepository proprietarioRepository;

	public void criarProprietario(Proprietario proprietario) {
		proprietarioRepository.save(proprietario);
	}

	public List<Proprietario> buscarTodosProprietarios() { return proprietarioRepository.findAll(); }

	public Proprietario buscarPorId(Integer id) {
		return proprietarioRepository.findById(id)
				.orElseThrow(() ->
						new RuntimeException("Nenhum proprietário encontrado para este ID"));
	}

	public void deletarPorId(Integer id) {
		proprietarioRepository.deleteById(id);
	}

	public void atualizarPorId(Integer id, Proprietario proprietarioAtt) {
		proprietarioAtt.setId(id);
		proprietarioRepository.save(proprietarioAtt);
	}

	public void validarDocumento(Proprietario proprietario) {
		if (proprietario.getTipoProprietario() == TipoProprietarioEnum.PF) {
			if (proprietario.getCpf() == null || proprietario.getCpf().isBlank()) {
				throw new RuntimeException("Proprietário Pessoa Física deve informar um CPF válido.");
			}

			if (proprietario.getCnpj() != null && !proprietario.getCnpj().isBlank()) {
				throw new RuntimeException("Proprietário PF não deve informar CNPJ.");
			}

		}

		if (proprietario.getTipoProprietario() == TipoProprietarioEnum.PJ) {
			if (proprietario.getCnpj() == null || proprietario.getCnpj().isBlank()) {
				throw new RuntimeException("Proprietário Pessoa Jurídica deve informar um CNPJ válido.");
			}

			if (proprietario.getCpf() != null && !proprietario.getCpf().isBlank()) {
				throw new RuntimeException("Proprietário PJ não deve informar CPF");
			}
		}

	}

}
