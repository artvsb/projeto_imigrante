package br.com.projetoimigrante.api.model;

import br.com.projetoimigrante.api.enums.TipoProprietarioEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "proprietario")
public class Proprietario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_proprietario", nullable = false)
	private TipoProprietarioEnum tipoProprietario;

	@Column(length = 11, unique = true)
	private String cpf;

	@Column(length = 14, unique = true)
	private String cnpj;

	private String telefone;
	private String email;
}
