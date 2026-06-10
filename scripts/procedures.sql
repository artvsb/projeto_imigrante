CREATE OR REPLACE PROCEDURE alocar_familia(
	p_id_familia INTEGER,
	p_id_alojamento INTEGER	
)
LANGUAGE plpgsql
AS $$
BEGIN 
	IF NOT EXISTS (
        SELECT 1
        FROM familia
        WHERE id = p_id_familia
          AND status = 'ATIVA'
    ) THEN
        RAISE EXCEPTION 'A família % não está ativa.', p_id_familia;
    END IF;
	IF NOT EXISTS (
		SELECT 1 
		FROM alojamento
		WHERE id = p_id_alojamento
		AND status = 'DISPONIVEL'
	) THEN 
		RAISE EXCEPTION 'O alojamento % não está disponível.', p_id_alojamento;
	END IF;	
	 IF EXISTS (
        SELECT 1
        FROM familia_alojamento
        WHERE id_familia = p_id_familia
          AND data_hora_saida IS NULL
    ) THEN
        RAISE EXCEPTION 'A família % já possui alojamento ativo.', p_id_familia;
    END IF;    
    IF EXISTS (
        SELECT 1
        FROM familia_alojamento
        WHERE id_alojamento = p_id_alojamento
          AND data_hora_saida IS NULL
    ) THEN
        RAISE EXCEPTION 'O alojamento % já está ocupado.', p_id_alojamento;
    END IF;
    INSERT INTO familia_alojamento (
        id_familia,
        id_alojamento
    )
    VALUES (
        p_id_familia,
        p_id_alojamento
    );
	RAISE NOTICE 
	'''
Família % alocada no alojamento nr. %
situado no endereço %
Cidade: % - %
''', 
	(SELECT nome_referencia FROM familia WHERE id = p_id_familia),
	p_id_alojamento,
	(SELECT logradouro FROM endereco WHERE id_alojamento = p_id_alojamento),
	(SELECT cidade FROM endereco WHERE id_alojamento = p_id_alojamento),
	(SELECT estado FROM endereco WHERE id_alojamento = p_id_alojamento);
END;
$$;


CREATE OR REPLACE PROCEDURE desalocar_familia(
    p_id_familia INTEGER
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_id_alojamento INTEGER;
BEGIN
    UPDATE familia_alojamento
    SET data_hora_saida = CURRENT_TIMESTAMP
    WHERE id_familia = p_id_familia
      AND data_hora_saida IS NULL
    RETURNING id_alojamento INTO v_id_alojamento;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'A família % não possui alojamento ativo.', p_id_familia;
    END IF;
    RAISE NOTICE 'Família % desalocada do alojamento % com sucesso.',
        p_id_familia,
        v_id_alojamento;
END;
$$;
