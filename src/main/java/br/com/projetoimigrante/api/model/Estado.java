package br.com.projetoimigrante.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Estado {

	@Id
	@Column(length = 2)
	private String sigla;

	private String nome;
}
