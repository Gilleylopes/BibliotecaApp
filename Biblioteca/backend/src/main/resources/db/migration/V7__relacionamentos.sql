-- Relacionamentos livro-autor
INSERT INTO livro_autor (livro_id, autor_id)
VALUES (1, 1), (2, 2), (3, 3)
ON CONFLICT DO NOTHING;

-- Relacionamentos livro-assunto
INSERT INTO livro_assunto (livro_id, assunto_id)
VALUES
(1, 1), 
(2, 2),
(3, 3)  
ON CONFLICT DO NOTHING;
