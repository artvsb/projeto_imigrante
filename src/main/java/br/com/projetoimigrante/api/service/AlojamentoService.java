package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.enums.StatusAlojamentoEnum;
import br.com.projetoimigrante.api.model.Alojamento;
import br.com.projetoimigrante.api.repository.AlojamentoRepository;
import br.com.projetoimigrante.api.repository.FamiliaAlojamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlojamentoService {

	@Autowired
	private AlojamentoRepository alojamentoRepository;

	@Autowired
	private FamiliaAlojamentoRepository familiaAlojamentoRepository;

	public Alojamento criarAlojamento(Alojamento alojamento) {
		alojamento.setStatus(StatusAlojamentoEnum.DISPONIVEL);
		return alojamentoRepository.save(alojamento);
	}

	public List<Alojamento> buscarTodosAlojamentos() {
		return alojamentoRepository.findAll();
	}

	public Alojamento buscarPorId(Long id) {
		return alojamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Alojamento não encontrado."));
	}

	public void deletarPorId(Long id) {
		alojamentoRepository.deleteById(id);
	}

	public Alojamento atualizarPorId(Long id, Alojamento alojamentoAtt) {
		Alojamento alojamento = buscarPorId(id);

		alojamento.setTamanhoM2(alojamentoAtt.getTamanhoM2());
		alojamento.setCustoMensal(alojamentoAtt.getCustoMensal());
		alojamento.setStatus(alojamentoAtt.getStatus());
		alojamento.setProprietario(alojamentoAtt.getProprietario());
		alojamento.setEndereco(alojamentoAtt.getEndereco());

		return alojamentoRepository.save(alojamento);
	}
}
