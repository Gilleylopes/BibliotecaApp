CREATE OR REPLACE VIEW vw_livros_autores_assuntos AS
SELECT 
  a.id AS autor_id,
  a.nome AS autor_nome,
  l.id AS livro_id,
  l.titulo,
  l.editora,
  l.edicao,
  l.ano_publicacao,
  l.valor,
  s.id AS assunto_id,
  s.descricao AS assunto
FROM livro l
JOIN livro_autor la ON la.livro_id = l.id
JOIN autor a ON a.id = la.autor_id
JOIN livro_assunto ls ON ls.livro_id = l.id
JOIN assunto s ON s.id = ls.assunto_id;
