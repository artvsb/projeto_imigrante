package br.com.projetoimigrante.api.model;

import br.com.projetoimigrante.api.enums.StatusFamiliaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "familia")
public class Familia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "familia", cascade = CascadeType.ALL)
	private List<Imigrante> membros = new ArrayList<>();

	@Column(name = "nome_referencia")
	private String nomeReferencia;

	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;

	@Enumerated(EnumType.STRING)
	private StatusFamiliaEnum status;
}
