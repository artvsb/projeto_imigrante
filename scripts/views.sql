-- visualizar o país de origem dos imigrantes, por estado da federação

CREATE OR REPLACE VIEW vw_origem_por_estado AS
	SELECT 
		e.estado,
		p.nome,
		COUNT(i.id) AS qtd_imigrantes
FROM endereco e
JOIN alojamento alj ON alj.id = e.id_alojamento
JOIN familia_alojamento fa ON fa.id_alojamento = alj.id
JOIN familia f ON f.id = fa.id_familia
JOIN imigrante i ON i.id_familia = f.id
JOIN pais p ON p.id = i.id_pais
GROUP BY e.estado, p.nome
ORDER BY 3 DESC;

SELECT * FROM vw_origem_por_estado;

-- mostrar quantos m2 tem por pessoa em cada alojamento

CREATE OR REPLACE VIEW vw_pessoas_alojamento AS 
SELECT 
	alj.id,
	alj.tamanho_m2,
	COUNT(i.id) AS qtd_imigrantes,
	(alj.tamanho_m2) / COUNT(i.id) AS m2_por_pessoa
	FROM alojamento alj 
	JOIN familia_alojamento fa ON fa.id_alojamento = alj.id
	JOIN familia f ON f.id = fa.id_familia
	JOIN imigrante i ON i.id_familia = fa.id_familia
	GROUP BY 1, 2
	ORDER BY 4 DESC;

SELECT * FROM vw_pessoas_alojamento;