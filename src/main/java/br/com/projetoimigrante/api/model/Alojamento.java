package br.com.projetoimigrante.api.model;

import br.com.projetoimigrante.api.enums.StatusAlojamentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "alojamento")
public class Alojamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "tamanho_m2")
	private Double tamanhoM2;

	@Column(name = "custo_mensal")
	private Double custoMensal;

	@Enumerated(EnumType.STRING)
	private StatusAlojamentoEnum status;

	@ManyToOne
	@JoinColumn(name = "id_proprietario", nullable = false)
	private Proprietario proprietario;

}
