-- Assuntos
INSERT INTO assunto (descricao, criado_por)
VALUES
('Romance', 'admin'),
('Biografia', 'admin'),
('Tecnologia', 'admin')
ON CONFLICT (descricao) DO NOTHING;