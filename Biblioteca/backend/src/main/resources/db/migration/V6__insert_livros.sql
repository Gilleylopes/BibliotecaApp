INSERT INTO livro (titulo, editora, edicao, ano_publicacao, valor, criado_por)
VALUES
  ('Amor em Tempos Modernos', 'Editora Central', 1, '2019', 49.90, 'admin'),
  ('Vida de um Brasileiro', 'Nova Letras', 1, '2021', 59.90, 'admin'),
  ('Introdução à Programação', 'TechBooks', 2, '2020', 69.90, 'admin')
ON CONFLICT (titulo) DO NOTHING;
