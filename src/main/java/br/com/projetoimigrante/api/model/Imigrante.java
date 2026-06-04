package br.com.projetoimigrante.api.model;

import br.com.projetoimigrante.api.enums.SexoEnum;
import br.com.projetoimigrante.api.enums.TipoMigranteEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "imigrante")
public class Imigrante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SexoEnum sexo;

	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(name = "nr_passaporte", unique = true)
	private String nrPassaporte;

	@Column(name = "rnm", unique = true)
	private String rnm;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_migrante", nullable = false)
	private TipoMigranteEnum tipoMigrante;

	@ManyToOne
	@JoinColumn(name = "id_pais",
		referencedColumnName = "id", nullable = false)
	private Pais pais;

	@ManyToOne
	@JoinColumn(name = "id_familia", nullable = false)
	private Familia familia;
}
