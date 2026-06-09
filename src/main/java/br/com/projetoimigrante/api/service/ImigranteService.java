package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.dto.ImigranteRequestDTO;
import br.com.projetoimigrante.api.dto.ImigranteResumoViewDTO;
import br.com.projetoimigrante.api.dto.QtdImigrantesPorRegiaoViewDTO;
import br.com.projetoimigrante.api.model.Familia;
import br.com.projetoimigrante.api.model.Imigrante;
import br.com.projetoimigrante.api.model.Pais;
import br.com.projetoimigrante.api.repository.FamiliaRepository;
import br.com.projetoimigrante.api.repository.ImigranteRepository;
import br.com.projetoimigrante.api.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImigranteService {

	@Autowired
	private ImigranteRepository imigranteRepository;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private FamiliaRepository familiaRepository;

	public void criarImigrante(ImigranteRequestDTO request) {
		Pais pais = paisRepository.findById(request.idPais())
				.orElseThrow(() -> new RuntimeException("País não encontrado"));

		Familia familia = familiaRepository.findById(request.idFamilia())
				.orElseThrow(() -> new RuntimeException("Família não encontrada"));

		Imigrante imigrante = new Imigrante();
		imigrante.setNome(request.nome());
		imigrante.setSexo(request.sexo());
		imigrante.setDataNascimento(request.dataNascimento());
		imigrante.setNrDocumento(request.nrDocumento());
		imigrante.setRefugiado(request.refugiado());
		imigrante.setPais(pais);
		imigrante.setFamilia(familia);

		imigranteRepository.save(imigrante);
	}
	public Imigrante buscarPorDocumento(String documento) {
		return imigranteRepository.findByNrDocumento(documento)
				.orElseThrow(() ->
						new RuntimeException("Nenhum imigrante encontrado para o documento informado."));
	}

	public List<Imigrante> buscarTodosImigrantes() { return imigranteRepository.findAll(); }

	public void deletarPorDocumento (String documento) {
		Imigrante imigrante = imigranteRepository
				.findByNrDocumento(documento)
				.orElseThrow(() ->
						new RuntimeException("Nenhum imigrante encontrado para o documento informado."));

		imigranteRepository.delete(imigrante);
	}

	public Imigrante atualizarPorDocumento(String documento, ImigranteRequestDTO request) {
		Imigrante imigrante = imigranteRepository
				.findByNrDocumento(documento)
				.orElseThrow(() ->
						new RuntimeException("Imigrante não encontrado."));

		Familia familia = familiaRepository.findById(request.idFamilia())
				.orElseThrow(() -> new RuntimeException("Família não encontrada."));

		Pais pais = paisRepository.findById(request.idPais())
				.orElseThrow(() -> new RuntimeException("País não encontrado."));

		imigrante.setNome(request.nome());
		imigrante.setSexo(request.sexo());
		imigrante.setDataNascimento(request.dataNascimento());
		imigrante.setPais(pais);
		imigrante.setFamilia(familia);

		return imigranteRepository.save(imigrante);
	}


}
