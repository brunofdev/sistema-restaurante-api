-- Criação da tabela cardapio no MySQL
CREATE TABLE cardapio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    disponibilidade BOOLEAN NOT NULL,
    dtinicio DATE NOT NULL,
    dtfim DATE NULL
);
drop table cardapio;
-- Inserção de dados exemplo
INSERT INTO cardapio (nome, descricao, disponibilidade, dtinicio, dtfim) VALUES
('Cardápio Principal', 'Cardápio padrão com pratos principais.', TRUE, '2025-01-01', NULL);

INSERT INTO cardapio (nome, descricao, disponibilidade, dtinicio, dtfim) VALUES
('Cardápio Promocional Verão', 'Ofertas especiais de verão com desconto.', TRUE, '2025-12-01', '2026-01-31');

INSERT INTO cardapio (nome, descricao, disponibilidade, dtinicio, dtfim) VALUES
('Cardápio Fim de Semana', 'Pratos especiais disponíveis apenas aos finais de semana.', FALSE, '2025-01-01', NULL);
