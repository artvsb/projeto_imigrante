package br.com.projetoimigrante.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "pais")
public class Pais {

	@Id
	@Column(length = 2)
	private String id;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_regiao", nullable = false)
	private Regiao regiao;
}
