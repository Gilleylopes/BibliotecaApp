-- Autores
INSERT INTO autor (nome, criado_por)
VALUES
('João Pereira', 'admin'),
('Ana Martins', 'admin'),
('Carlos Silva', 'admin')
ON CONFLICT (nome) DO NOTHING;