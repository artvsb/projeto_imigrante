package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.dto.CriarAlojamentoRequestDTO;
import br.com.projetoimigrante.api.enums.StatusAlojamentoEnum;
import br.com.projetoimigrante.api.model.Alojamento;
import br.com.projetoimigrante.api.model.Endereco;
import br.com.projetoimigrante.api.model.Proprietario;
import br.com.projetoimigrante.api.repository.AlojamentoRepository;
import br.com.projetoimigrante.api.repository.EnderecoRepository;
import br.com.projetoimigrante.api.repository.FamiliaAlojamentoRepository;
import br.com.projetoimigrante.api.repository.ProprietarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlojamentoService {

	@Autowired
	private AlojamentoRepository alojamentoRepository;

	@Autowired
	private ProprietarioRepository proprietarioRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private FamiliaAlojamentoRepository familiaAlojamentoRepository;


	@Transactional
	public void criarAlojamento(CriarAlojamentoRequestDTO request) {
		Proprietario proprietario = proprietarioRepository.findById(request.idProprietario())
				.orElseThrow(() -> new RuntimeException("Proprietário não encontrado."));

		Alojamento alojamento = new Alojamento();
		alojamento.setTamanhoM2(request.tamanhoM2());
		alojamento.setCustoMensal(request.custoMensal());
		alojamento.setStatus(request.status());
		alojamento.setProprietario(proprietario);

		Alojamento alojamentoSalvo = alojamentoRepository.save(alojamento);

		Endereco endereco = new Endereco();
		endereco.setLogradouro(request.endereco().logradouro());
		endereco.setCidade(request.endereco().cidade());
		endereco.setEstado(request.endereco().estado());
		endereco.setCep(request.endereco().cep());
		endereco.setAlojamento(alojamentoSalvo);

		enderecoRepository.save(endereco);
	}

	public List<Alojamento> buscarTodosAlojamentos() {
		return alojamentoRepository.findAll();
	}

	public Alojamento buscarPorId(Integer id) {
		return alojamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Alojamento não encontrado."));
	}

	public void deletarPorId(Integer id) {
		alojamentoRepository.deleteById(id);
	}

	public Alojamento atualizarPorId(Integer id, Alojamento alojamentoAtt) {
		Alojamento alojamento = buscarPorId(id);

		alojamento.setTamanhoM2(alojamentoAtt.getTamanhoM2());
		alojamento.setCustoMensal(alojamentoAtt.getCustoMensal());
		alojamento.setStatus(alojamentoAtt.getStatus());
		alojamento.setProprietario(alojamentoAtt.getProprietario());

		return alojamentoRepository.save(alojamento);
	}

	public List<Alojamento> buscarPorProprietario(Integer proprietarioId) {
		return alojamentoRepository.findByProprietarioId(proprietarioId);
	}
}
