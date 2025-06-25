-- Usuários
INSERT INTO usuario (username, nome, email, senha, criado_em)
VALUES
('admin', 'Admin da Silva', 'admin@biblioteca.com', '$2a$10$.lY39ClM4b/xzB626Np71u94OnM2OQuA2WJcGjb.24UVK3BHmkVzC', NOW()),
('gilley', 'Gilley do Vale', 'gilley@biblioteca.com', '$2a$10$PyhqnQM7YRsaQ/XfiBH5m.DUTUVfqa8jQPBKncBi3PpMiVLwt1Wku', NOW())
ON CONFLICT (username) DO NOTHING;

-- Associações usuário-perfil
INSERT INTO usuario_role (usuario_id, role_id)
VALUES (1, 1), (2, 2)
ON CONFLICT DO NOTHING;

-- Tokens fictícios
INSERT INTO refresh_token (token, expira_em, revogado, usuario_id, criado_em)
VALUES
('abc123', NOW() + INTERVAL '7 days', FALSE, 1, NOW()),
('xyz789', NOW() + INTERVAL '7 days', FALSE, 2, NOW())
ON CONFLICT (token) DO NOTHING;