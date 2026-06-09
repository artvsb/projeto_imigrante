-- resumo imigrantes 

CREATE OR REPLACE VIEW vw_imigrantes_resumo AS
SELECT
    i.nome,
    i.data_nascimento,
    p.nome AS pais_origem
FROM imigrante i
JOIN pais p ON p.id = i.id_pais;

SELECT * FROM vw_imigrantes_resumo;



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

CREATE OR REPLACE VIEW vw_m2_por_pessoa_alojamento AS
SELECT
    a.id AS id_alojamento,
    e.cidade,
    e.estado,
    a.tamanho_m2,
    f.id AS id_familia,
    f.nome_referencia,
    COUNT(i.id) AS quantidade_pessoas,
    a.tamanho_m2 / COUNT(i.id) AS m2_por_pessoa
FROM alojamento a
JOIN familia_alojamento fa
    ON fa.id_alojamento = a.id
   AND fa.data_hora_saida IS NULL
JOIN familia f
    ON f.id = fa.id_familia
JOIN imigrante i
    ON i.id_familia = f.id
LEFT JOIN endereco e
    ON e.id_alojamento = a.id
GROUP BY
    a.id,
    e.cidade,
    e.estado,
    a.tamanho_m2,
    f.id,
    f.nome_referencia;

SELECT * FROM vw_m2_por_pessoa_alojamento;

-- nr de imigrantes e refugiados por estado


CREATE OR REPLACE VIEW vw_imigrantes_refugiados_por_estado AS
SELECT
    e.estado,
    COUNT(i.id) AS total_pessoas,
    COUNT(i.id) FILTER (WHERE i.refugiado = true) AS total_refugiados
FROM endereco e
JOIN alojamento a
    ON a.id = e.id_alojamento
JOIN familia_alojamento fa
    ON fa.id_alojamento = a.id
   AND fa.data_hora_saida IS NULL
JOIN familia f
    ON f.id = fa.id_familia
JOIN imigrante i
    ON i.id_familia = f.id
GROUP BY e.estado;

SELECT * FROM vw_imigrantes_refugiados_por_estado;


-- imigrantes por regiao

CREATE OR REPLACE VIEW vw_imigrantes_por_regiao AS
SELECT
    r.id AS id_regiao,
    r.nome AS regiao,
    COUNT(i.id) AS total_imigrantes
FROM regiao r
JOIN pais p
    ON p.id_regiao = r.id
JOIN imigrante i
    ON i.id_pais = p.id
GROUP BY
    r.id,
    r.nome;

SELECT * FROM vw_imigrantes_por_regiao;