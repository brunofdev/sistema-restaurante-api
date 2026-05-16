-- Adiciona coluna gatilho ao outbox_eventos para registrar o disparador de cada TipoEvento
ALTER TABLE outbox_eventos
    ADD COLUMN gatilho VARCHAR(100) NOT NULL DEFAULT 'SISTEMA';
