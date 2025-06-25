# 📚 BibliotecaApp — Sistema de Gerenciamento de Livros

Sistema completo para cadastro, gerenciamento e consulta de **livros**, **autores** e **assuntos**, com autenticação via JWT, geração de relatórios em PDF e interface moderna em Angular.

---

## 🧰 Tecnologias Utilizadas

### 💻 Backend
- Java 17 + Spring Boot
- Spring Web, Spring Data JPA, Spring Security
- Bean Validation
- JasperReports (relatórios PDF)
- Flyway (migração de banco)
- PostgreSQL
- Maven

### 🌐 Frontend
- Angular 17+ com Standalone Components
- Bootstrap 5
- Ng-Select
- ngx-toastr
- ngx-mask
- Reactive Forms

### 🔐 Segurança
- Autenticação com JWT (access + refresh token)
- Proteção de rotas com `AuthGuard`
- Limpeza automática de tokens ao acessar a tela de login

### ⚙️ DevOps
- Docker + Docker Compose
- CI/CD com Azure DevOps (build, test, deploy)
- NGINX (serviço estático do frontend)

---

## 📁 Estrutura do Projeto


bibliotecaapp/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── br/com/tjrj/biblioteca/
│   │   │   │       ├── controller/
│   │   │   │       ├── dto/
│   │   │   │       ├── entity/
│   │   │   │       ├── exception/
│   │   │   │       ├── repository/
│   │   │   │       ├── security/
│   │   │   │       ├── service/
│   │   │   │       └── util/
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       ├── messages.properties
│   │   │       └── relatorios/
│   │   └── test/
│   ├── pom.xml
│   ├── Dockerfile
│   
│
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   ├── assets/
│   │   └── styles.scss
│   ├── angular.json
│   ├── package.json
│   ├── Dockerfile
│   └── nginx.conf
│
├── README.md
└── docker-compose.yml

---

## 🔐 Autenticação com JWT

A aplicação utiliza **JWT** para autenticação e controle de sessão.

| Ação         | Rota                             | Acesso |
|--------------|----------------------------------|--------|
| Login        | `POST /api/v1/auth/login`        | Público |
| Refresh      | `POST /api/v1/auth/refresh`      | Público |
| Demais APIs  | `/api/v1/**`                     | Protegidas |

- O token é enviado no header: `Authorization: Bearer <token>`
- O frontend armazena o token no `localStorage` e limpa ao iniciar a tela de login.

---

## 🧾 Documentação da API — Swagger

- Acesse: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Baseada em **OpenAPI 3**
- Todas as rotas documentadas com exemplos

---

⚙️ Deploy do Projeto com Docker Compose

✅ Requisitos
Docker e Docker Compose instalados

▶️ Passo a passo:
bash
git clone <url-do-repositorio>
cd bibliotecaapp
docker-compose up --build

🌐 Acesse as aplicações
Serviço	        URL
Frontend	    http://localhost:3000
Backend (API)	http://localhost:8080
Swagger UI	    http://localhost:8080/swagger-ui.html

🗄️ Banco de Dados
Host: localhost
Porta: 5432

Usuário/Senha: conforme .env ou docker-compose.yml

⏹️ Parar os serviços
bash
docker-compose down

👤 Usuários para Avaliação

Usuário	    Senha	    Perfil
admin	    admin123	Administrador
gilley	    gilley123	Usuário padrão

🧪 Testes
Backend (JUnit 5 + Mockito)
Para Executar os testes manualmente

bash
cd bibliotecaapp 

 docker run --rm \
  -v "$PWD/backend":/app \
  -w /app \
  maven:3.9.2-eclipse-temurin-17 \
  mvn -Dtest=AutorServiceTest,AssuntoServiceTest,LivroServiceTest test
