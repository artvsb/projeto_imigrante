package br.com.projetoimigrante.api.service;

import br.com.projetoimigrante.api.enums.TipoMigranteEnum;
import br.com.projetoimigrante.api.model.Imigrante;
import br.com.projetoimigrante.api.repository.ImigranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImigranteService {

	@Autowired
	private ImigranteRepository imigranteRepository;

	public void criarImigrante(Imigrante imigrante) {
		imigranteRepository.save(imigrante);
	}

	public Imigrante buscarPorDocumento(String documento) {
		return imigranteRepository.findByNrPassaporte(documento)
				.or(() -> imigranteRepository.findByRnm(documento))
				.orElseThrow(() ->
						new RuntimeException("Nenhum imigrante encontrado para o documento informado."));
	}

	public List<Imigrante> buscarTodosImigrantes() { return imigranteRepository.findAll(); }

	public void deletarPorDocumento (String documento) {
		Imigrante imigrante = imigranteRepository
				.findByNrPassaporte(documento)
				.or(() -> imigranteRepository.findByRnm(documento))
				.orElseThrow(() ->
						new RuntimeException("Nenhum imigrante encontrado para o documento informado."));

		imigranteRepository.delete(imigrante);
	}

	public Imigrante atualizarPorDocumento(String documento, Imigrante imigranteAtt) {
		Imigrante imigrante = imigranteRepository
				.findByNrPassaporte(documento)
				.or(() -> imigranteRepository.findByRnm(documento))
				.orElseThrow(() ->
						new RuntimeException("Imigrante não encontrado."));

		imigrante.setNome(imigranteAtt.getNome());
		imigrante.setSexo(imigranteAtt.getSexo());
		imigrante.setDataNascimento(imigranteAtt.getDataNascimento());
		imigrante.setPais(imigranteAtt.getPais());
		imigrante.setFamilia(imigranteAtt.getFamilia());

		return imigranteRepository.save(imigrante);
	}

	public void validarDocumentos(Imigrante imigrante) {

		if (imigrante.getTipoMigrante() == TipoMigranteEnum.REFUGIADO) {

			if (imigrante.getRnm() == null ||
					imigrante.getRnm().isBlank()) {

				throw new RuntimeException(
						"Refugiado deve informar o RNM."
				);
			}

			if (imigrante.getNrPassaporte() != null &&
					!imigrante.getNrPassaporte().isBlank()) {

				throw new RuntimeException(
						"Refugiado não deve informar passaporte."
				);
			}
		}

		if (imigrante.getTipoMigrante() == TipoMigranteEnum.IMIGRANTE) {

			if (imigrante.getNrPassaporte() == null ||
					imigrante.getNrPassaporte().isBlank()) {

				throw new RuntimeException(
						"Imigrante deve informar o número do passaporte."
				);
			}

			if (imigrante.getRnm() != null &&
					!imigrante.getRnm().isBlank()) {

				throw new RuntimeException(
						"Imigrante não deve informar RNM."
				);
			}
		}
	}
}
