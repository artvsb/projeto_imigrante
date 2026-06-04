CREATE TABLE pais (
    id CHAR(2) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    continente VARCHAR(50),
    id_regiao INTEGER NOT NULL REFERENCES regiao(id);

SELECT * FROM regiao;
SELECT * FROM pais;

UPDATE pais
SET id_regiao = 4 WHERE nome = 'México'

CREATE TABLE regiao (
	id serial PRIMARY KEY,
	nome VARCHAR(100) NOT NULL UNIQUE
)

CREATE TABLE proprietario (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(200) NOT NULL,
	cpf_cnpj VARCHAR(14) NOT NULL UNIQUE,
	telefone VARCHAR(50),
	email VARCHAR(100)
);

SELECT
    r.nome AS regiao,
    COUNT(i.id) AS qtd_refugiados
FROM imigrante i
JOIN pais p
    ON p.id = i.id_pais
JOIN regiao r
    ON r.id = p.id_regiao
WHERE i.tipo_migrante = 'refugiado'
GROUP BY r.nome
ORDER BY qtd_refugiados DESC;

CREATE TABLE alojamento (
    id SERIAL PRIMARY KEY,
    id_proprietario INTEGER NOT NULL REFERENCES proprietario(id),
    tamanho_m2 NUMERIC(10,2) NOT NULL,
    aluguel NUMERIC NOT NULL,
    status VARCHAR(50) DEFAULT 'ativo'
);

CREATE TABLE endereco (
	id SERIAL PRIMARY KEY,	
	id_alojamento INTEGER NOT NULL UNIQUE REFERENCES alojamento(id),
	logradouro VARCHAR(200),
    cidade VARCHAR(100) NOT NULL,
    estado CHAR(2) NOT NULL REFERENCES estado(sigla),
    cep CHAR(8)	
);

CREATE TABLE familia (
    id SERIAL PRIMARY KEY,
    nome_referencia VARCHAR(150),
    data_cadastro DATE DEFAULT CURRENT_DATE,
    status VARCHAR(50) DEFAULT 'ativa'
); 


CREATE TABLE imigrante (
    id SERIAL PRIMARY KEY,
    id_familia INTEGER REFERENCES familia(id),
    nome VARCHAR(200) NOT NULL,
    id_pais CHAR(2) REFERENCES pais(id),
    nr_passaporte VARCHAR(20) UNIQUE,
    sexo CHAR(1) NOT NULL,
    data_nascimento DATE,
    tipo_migrante VARCHAR(50) NOT NULL,
    CONSTRAINT chk_sexo_imigrante
        CHECK (sexo IN ('M', 'F')),
    CONSTRAINT chk_tipo_migrante
        CHECK (tipo_migrante IN ('refugiado', 'imigrante')),
    CONSTRAINT chk_passaporte_obrigatorio
        CHECK (
            tipo_migrante = 'refugiado'
            OR nr_passaporte IS NOT NULL
        )
);


CREATE TABLE familia_alojamento (
    id SERIAL PRIMARY KEY,
    id_familia INTEGER NOT NULL REFERENCES familia(id),
    id_alojamento INTEGER NOT NULL REFERENCES alojamento(id),
    data_hora_entrada TIMESTAMP NOT NULL,
    data_hora_saida TIMESTAMP,
    status VARCHAR(50) DEFAULT 'ativo',
    CONSTRAINT uq_familia_alojamento
		UNIQUE (id_familia, id_alojamento);
);

CREATE TABLE estado (
	sigla CHAR(2) PRIMARY KEY,
	nome varchar(100) NOT NULL);
