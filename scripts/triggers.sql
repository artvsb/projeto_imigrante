-- data/hora do cadastro do imigrante

CREATE OR REPLACE FUNCTION imig_data_hora_cadastro()
RETURNS TRIGGER AS $$
BEGIN
    NEW.data_hora_cadastro = now();
	NEW.data_hora_ultima_alteracao = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_imig_novo_cadastro
BEFORE INSERT ON imigrante
FOR EACH ROW
EXECUTE FUNCTION imig_data_hora_cadastro();

-- data/hora da ultima alteracao cadastral: 

CREATE OR REPLACE FUNCTION fn_imigrante_data_ultima_alteracao()
RETURNS TRIGGER AS $$
BEGIN
    NEW.data_hora_ultima_alteracao = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_imigrante_data_ultima_alteracao
BEFORE UPDATE ON imigrante
FOR EACH ROW
EXECUTE FUNCTION fn_imigrante_data_ultima_alteracao();


-- atualiza status do alojamento quando ocupado 

CREATE OR REPLACE FUNCTION att_status_alojamento_entrada()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE alojamento
    SET status = 'INDISPONIVEL'
    WHERE id = NEW.id_alojamento;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_att_status_alojamento_entrada
AFTER INSERT ON familia_alojamento
FOR EACH ROW 
EXECUTE FUNCTION att_status_alojamento_entrada();



-- atualiza data/hora de entrada da família em um alojamento

CREATE OR REPLACE FUNCTION entrada_familia_alojamento()
 RETURNS TRIGGER
AS $$ 
BEGIN 
	NEW.data_hora_entrada := now();	
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER entrada_familia_alojamento 
BEFORE INSERT ON familia_alojamento 
FOR EACH ROW 
EXECUTE FUNCTION entrada_familia_alojamento();

-- libera alojamentos disponiveis apos saida de familia

CREATE OR REPLACE FUNCTION liberar_alojamento_apos_saida()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    IF OLD.data_hora_saida IS NULL
       AND NEW.data_hora_saida IS NOT NULL THEN

        UPDATE alojamento
        SET status = 'DISPONIVEL'
        WHERE id = NEW.id_alojamento;

    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_liberar_alojamento_apos_saida
AFTER UPDATE OF data_hora_saida ON familia_alojamento
FOR EACH ROW
EXECUTE FUNCTION liberar_alojamento_apos_saida();


-- validar documento do proprietário

CREATE OR REPLACE FUNCTION validar_documento_proprietario()
RETURNS trigger AS $$
BEGIN
    IF NEW.tipo_proprietario = 'PF' THEN
        IF NULLIF(BTRIM(NEW.cpf), '') IS NULL THEN
            RAISE EXCEPTION 'Proprietário PF deve informar CPF.';
        END IF;

        IF NEW.cpf !~ '^[0-9]{11}$' THEN
            RAISE EXCEPTION 'CPF deve ter exatamente 11 dígitos numéricos.';
        END IF;

        IF NULLIF(BTRIM(NEW.cnpj), '') IS NOT NULL THEN
            RAISE EXCEPTION 'Proprietário PF não deve informar CNPJ.';
        END IF;
    END IF;

    IF NEW.tipo_proprietario = 'PJ' THEN
        IF NULLIF(BTRIM(NEW.cnpj), '') IS NULL THEN
            RAISE EXCEPTION 'Proprietário PJ deve informar CNPJ.';
        END IF;

        IF NEW.cnpj !~ '^[0-9]{14}$' THEN
            RAISE EXCEPTION 'CNPJ deve ter exatamente 14 dígitos numéricos.';
        END IF;

        IF NULLIF(BTRIM(NEW.cpf), '') IS NOT NULL THEN
            RAISE EXCEPTION 'Proprietário PJ não deve informar CPF.';
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_validar_documento_proprietario
BEFORE INSERT OR UPDATE ON proprietario
FOR EACH ROW
EXECUTE FUNCTION validar_documento_proprietario();


	