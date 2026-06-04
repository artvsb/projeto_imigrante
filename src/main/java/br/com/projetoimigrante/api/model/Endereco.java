package br.com.projetoimigrante.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_alojamento", nullable = false, unique = true)
	private Alojamento alojamento;

	private String logradouro;
	private String cidade;

	@ManyToOne
	@JoinColumn(name = "estado",
		referencedColumnName = "sigla", nullable = false)
	private Estado estado;
}
