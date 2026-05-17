ALTER TABLE clientes DROP COLUMN ponto_fidelidade;
ALTER TABLE clientes ADD CONSTRAINT uc_clientes_fidelidade_referencia_id UNIQUE (fidelidade_referencia_id);
