SELECT * FROM imigrante;
SELECT * FROM endereco;
SELECT * FROM pais;
SELECT * FROM 

-- atualiza data de entrada da família em um alojamento

CREATE OR REPLACE FUNCTION entrada_familia_alojamento()
 RETURNS TRIGGER
 LANGUAGE plpgsql
AS $$ 
BEGIN 
	NEW.data_entrada := now();	
	RETURN NEW;
END;
$$;

CREATE TRIGGER entrada_familia_alojamento BEFORE
INSERT ON familia_alojamento 
FOR EACH ROW 
EXECUTE FUNCTION entrada_familia_alojamento();


-- verifica se a data de saida da família já passou (caso haja)
CREATE OR REPLACE FUNCTION verificar_data_saida()
RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.data_saida IS NOT NULL
       AND NEW.data_saida <= CURRENT_DATE
    THEN
        NEW.status := 'inativo';
    ELSE
        NEW.status := 'ativo';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_verificar_data_saida
BEFORE INSERT OR UPDATE
ON familia_alojamento
FOR EACH ROW
EXECUTE FUNCTION verificar_data_saida();


-- impedir a ocorrencia de dois alojamentos simultaneos 








		

	