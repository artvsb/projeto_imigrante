package br.com.projetoimigrante.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "familia_alojamento")
public class FamiliaAlojamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_familia", nullable = false)
	private Familia familia;

	@ManyToOne
	@JoinColumn(name = "id_alojamento", nullable = false)
	private Alojamento alojamento;

	@Column(name = "data_hora_entrada", nullable = false)
	private LocalDateTime dataHoraEntrada;

	@Column(name = "data_hora_saida")
	private LocalDateTime dataHoraSaida;
}
