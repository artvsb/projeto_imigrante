-- resumo imigrantes 

CREATE OR REPLACE VIEW vw_imigrantes_resumo AS
SELECT
    i.nome,
    i.data_nascimento,
    p.nome AS pais_origem
FROM imigrante i
JOIN pais p ON p.id = i.id_pais;

SELECT * FROM vw_imigrantes_resumo;



-- visualizar alojamentos disponíveis, endereço e contato proprietário

CREATE OR REPLACE VIEW vw_alojamentos_disponiveis AS
SELECT
    a.id AS id_alojamento,
    a.tamanho_m2,
    a.custo_mensal,
    a.status,
    e.logradouro,
    e.cidade,
    e.estado,
    e.cep,
    p.id AS id_proprietario,
    p.nome AS nome_proprietario,
    p.telefone AS telefone_proprietario,
    p.email AS email_proprietario
FROM alojamento a
JOIN proprietario p
    ON p.id = a.id_proprietario
LEFT JOIN endereco e
    ON e.id_alojamento = a.id
WHERE a.status = 'DISPONIVEL'
ORDER BY a.custo_mensal;

SELECT * FROM vw_alojamentos_disponiveis;



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


CREATE OR REPLACE VIEW vw_custo_por_imigrante_por_pais AS 
WITH total_pessoas_por_alojamento AS (
	SELECT 
		a.id AS id_alojamento,
		count(i.id) AS total_pessoas
	FROM alojamento a
	JOIN familia_alojamento fa
	ON fa.id_alojamento = a.id AND fa.data_hora_saida IS NULL 
	JOIN familia f
	ON f.id = fa.id_familia
	JOIN imigrante i
	ON i.id_familia = f.id
	GROUP BY a.id
)
SELECT 
	p.id AS id_pais,
	p.nome AS nome_pais,
	count(i.id) AS qtd_imigrantes,
	round(avg(a.custo_mensal / tpa.total_pessoas)::NUMERIC, 2) AS custo_medio_pessoa
FROM pais p
JOIN imigrante i ON p.id = i.id_pais
JOIN familia f ON f.id = i.id_familia
JOIN familia_alojamento fa ON fa.id_familia = f.id 
AND fa.data_hora_saida IS NULL 
JOIN alojamento a ON fa.id_alojamento = a.id
JOIN total_pessoas_por_alojamento tpa ON tpa.id_alojamento = a.id
GROUP BY 1, 2
ORDER BY 4 DESC;

SELECT * FROM vw_custo_por_imigrante_por_pais;

-- quantidde de refugiados por pais:

CREATE OR REPLACE VIEW vw_refugiados_por_pais AS 
SELECT 
	p.id AS id_pais,
	p.nome AS pais_origem,
	COUNT(i.id) AS total_refugiados
FROM pais p
JOIN imigrante i
	ON i.id_pais = p.id
WHERE i.refugiado = TRUE
GROUP BY 1, 2
ORDER BY 3 DESC;

SELECT * FROM vw_refugiados_por_pais;


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


-- imigrantes por regiao:

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

