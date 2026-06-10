CREATE TABLE regiao (
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL UNIQUE
);

DROP DATABASE projeto_imigrante;

CREATE TABLE pais (
    id CHAR(2) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_regiao BIGINT NOT NULL REFERENCES regiao(id)
    );

CREATE TABLE familia (
    id BIGSERIAL PRIMARY KEY,
    nome_referencia VARCHAR(150) NOT NULL,
    data_cadastro DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT chk_familia_status
        CHECK (status IN ('ATIVA', 'INATIVA'))
);

CREATE TABLE imigrante (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    nr_documento VARCHAR(30) NOT NULL,
    sexo VARCHAR(20) NOT NULL,
    data_nascimento DATE NOT NULL,
    id_pais CHAR(2) NOT NULL REFERENCES pais(id),
    id_familia BIGINT NOT NULL REFERENCES familia(id),
    refugiado BOOLEAN NOT NULL,
    CONSTRAINT chk_sexo
        CHECK (sexo IN ('MASCULINO', 'FEMININO')),
	CONSTRAINT chk_nr_documento_nao_vazio
		CHECK (btrim(nr_documento) <> ''),
	CONSTRAINT uk_imigrante_nr_documento 
		UNIQUE (nr_documento)
);


CREATE TABLE proprietario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    tipo_proprietario CHAR(2) NOT NULL,
    cpf VARCHAR(11) UNIQUE,
    cnpj VARCHAR(14) UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(150),
    CONSTRAINT chk_tipo_proprietario
        CHECK (tipo_proprietario IN ('PF', 'PJ'))
);

SELECT * FROM proprietario;


CREATE TABLE alojamento (
    id BIGSERIAL PRIMARY KEY,
    tamanho_m2 NUMERIC(10,2),
    custo_mensal NUMERIC(10,2),
    status VARCHAR(20) NOT NULL DEFAULT 'DISPONÍVEL',
    id_proprietario BIGINT NOT NULL REFERENCES proprietario(id),    
    CONSTRAINT chk_status_alojamento
        CHECK (status IN ('DISPONIVEL', 'INDISPONIVEL')),
    CONSTRAINT chk_tamanho_m2
        CHECK (tamanho_m2 IS NULL OR tamanho_m2 > 0),
    CONSTRAINT chk_custo_mensal
        CHECK (custo_mensal IS NULL OR custo_mensal >= 0)
);

CREATE TABLE familia_alojamento (
    id BIGSERIAL PRIMARY KEY,
    id_familia BIGINT NOT NULL REFERENCES familia(id),
    id_alojamento BIGINT NOT NULL REFERENCES alojamento(id),
    data_hora_entrada TIMESTAMP NOT NULL,
    data_hora_saida TIMESTAMP
);

CREATE TABLE estado (
	sigla CHAR(2) PRIMARY KEY,
	nome varchar(100) NOT NULL);


CREATE TABLE endereco (
	id BIGSERIAL PRIMARY KEY,	
	id_alojamento BIGINT NOT NULL UNIQUE REFERENCES alojamento(id),
	logradouro VARCHAR(200),
    cidade VARCHAR(100) NOT NULL,
    estado CHAR(2) NOT NULL REFERENCES estado(sigla),
    cep CHAR(8) NOT NULL
);


