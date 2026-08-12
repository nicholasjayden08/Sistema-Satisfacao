# Padá Padoca — Sistema de Satisfação

Sistema de pesquisa de satisfação para a Padá Padoca (Ponta Grossa, PR). O cliente avalia a experiência pelo celular via QR code, e o dono acompanha tudo em um painel administrativo com estatísticas e histórico de respostas.

## Como funciona, na prática

1. Um QR code fica disponível na loja, apontando para a página de pesquisa.
2. O cliente escaneia, avalia (estrelas, tempo de espera, como conheceu a padaria, comentário livre) e envia.
3. Se a nota média for boa (≥ 4), o sistema convida o cliente a deixar uma avaliação no Google. Se não, mostra uma mensagem de agradecimento simples.
4. O dono acessa o painel administrativo (protegido por login) e acompanha as avaliações, médias por categoria e gráficos de evolução.

## Estrutura do repositório

```
Sistema-Satisfacao/
├── backend/                     API Java (Spring Boot)
│   ├── src/main/java/com/padapadoca/api/
│   │   ├── controller/           endpoints REST
│   │   ├── service/               regras de negócio
│   │   ├── repository/            acesso ao banco (Spring Data JPA)
│   │   ├── model/                 entidade Avaliacao
│   │   ├── dto/                   objetos de entrada/saída da API
│   │   └── config/                segurança (Basic Auth) e CORS
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── Dockerfile
│   └── pom.xml
└── docs/                         Frontend (HTML/CSS/JS puro, sem build)
    ├── index.html                 landing simples com os dois links
    ├── pesquisa-satisfacao.html   página que o cliente abre pelo QR code
    └── painel-admin.html          painel do dono da padaria
```

## Tech stack

| Camada | Tecnologia |
|---|---|
| Backend | Java 21, Spring Boot 4, Spring Security (Basic Auth), Spring Data JPA |
| Banco de dados | PostgreSQL |
| Frontend | HTML, CSS e JavaScript puros — sem framework, sem build, sem npm |
| Hospedagem backend | [Render](https://render.com) (Web Service via Docker + PostgreSQL gerenciado) |
| Hospedagem frontend | [GitHub Pages](https://pages.github.com) (pasta `/docs`) |
| Gráficos do painel | [Chart.js](https://www.chartjs.org) via CDN |

## Rodando localmente

### Backend

Pré-requisitos: Java 21 e um PostgreSQL rodando localmente (ou acessível via URL).

```bash
cd backend
export DATABASE_URL=jdbc:postgresql://localhost:5432/padapadoca
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=sua_senha_local
mvn spring-boot:run
```

A API sobe em `http://localhost:8081` por padrão (configurável via variável `PORT`).

> **Atenção:** desde a migração para PostgreSQL, o backend **não sobe sem essas três variáveis de ambiente** configuradas — não há mais banco H2 de fallback para desenvolvimento local.

### Frontend

Não precisa de build nem de servidor especial — são arquivos estáticos. Basta abrir os arquivos de `docs/` diretamente no navegador, ou servir a pasta com qualquer servidor estático simples (ex: extensão "Live Server" do VS Code).

Para testar contra a API local, atualize temporariamente a constante `API_URL` (em `pesquisa-satisfacao.html`) e `API_BASE` (em `painel-admin.html`) para `http://localhost:8081`. **Lembre de reverter para a URL de produção antes de commitar.**

## Deploy

### Backend (Render)

- Tipo: Web Service, build via Docker (usa o `backend/Dockerfile`).
- URL de produção: `https://padapadoca-api.onrender.com`
- Variáveis de ambiente configuradas no painel do Render (aba **Environment**):

| Variável | Descrição |
|---|---|
| `DATABASE_URL` | URL JDBC do PostgreSQL (fornecida automaticamente se o banco Postgres foi criado no mesmo workspace do Render) |
| `DATABASE_USERNAME` | Usuário do banco |
| `DATABASE_PASSWORD` | Senha do banco |
| `PADAPADOCA_ADMIN_USERNAME` | Usuário de login do painel administrativo |
| `PADAPADOCA_ADMIN_PASSWORD` | Senha de login do painel administrativo |
| `PORT` | Injetada automaticamente pelo Render — não precisa configurar manualmente |

> O plano gratuito do Render "hiberna" o serviço após período de inatividade — a primeira requisição do dia pode demorar de 30 a 50 segundos para responder enquanto o serviço acorda. Bancos Postgres gratuitos no Render também expiram 30 dias após a criação; para uso contínuo com o cliente, considerar upgrade de plano antes do vencimento.

### Frontend (GitHub Pages)

- Configurado em **Settings → Pages**, servindo a partir da branch `main`, pasta `/docs`.
- URL de produção: `https://nicholasjayden08.github.io/Sistema-Satisfacao/`
- Deploy automático a cada push na `main` (via GitHub Actions, gerenciado pelo próprio GitHub Pages).

## Endpoints principais da API

Todos os endpoints de leitura exigem autenticação HTTP Basic (usuário/senha configurados via `PADAPADOCA_ADMIN_USERNAME` / `PADAPADOCA_ADMIN_PASSWORD`). O envio de avaliação é público, por design — é o endpoint que o cliente final usa pelo QR code.

| Método | Endpoint | Autenticação | Descrição |
|---|---|---|---|
| `POST` | `/api/avaliacoes` | Não | Envia uma nova avaliação |
| `GET` | `/api/avaliacoes` | Sim | Lista avaliações (paginado, suporta `page`, `size`, `sort`, e filtro por período com `inicio`/`fim`) |
| `GET` | `/api/avaliacoes/estatisticas` | Sim | Retorna médias gerais e por categoria, total de avaliações e percentual de avaliações positivas |

## Campos da avaliação

| Campo | Obrigatório | Tipo | Observação |
|---|---|---|---|
| `nomeCliente` | Sim | texto | |
| `notaGeral` | Sim | 1–5 | |
| `notaAtendimento` | Não | 1–5 | |
| `notaProdutos` | Não | 1–5 | |
| `notaAmbiente` | Não | 1–5 | |
| `tempoEspera` | Não | `MUITO_RAPIDO` \| `RAPIDO` \| `NORMAL` \| `DEMORADO` \| `MUITO_DEMORADO` | |
| `comoConheceu` | Não | `INDICACAO` \| `INSTAGRAM` \| `GOOGLE` \| `JA_SOU_CLIENTE` | |
| `comentario` | Não | texto livre | |

## Decisões técnicas relevantes

- **Migração de H2 para PostgreSQL:** o serviço web do Render usa filesystem efêmero — qualquer arquivo escrito em disco (como o banco H2 baseado em arquivo) se perde a cada reinício ou hibernação do serviço. O PostgreSQL, rodando como serviço gerenciado separado, resolve isso.
- **`spring.jpa.hibernate.ddl-auto=update`:** campos novos no modelo (`Avaliacao`) geram colunas novas automaticamente no banco a cada deploy. Colunas removidas do código **não são apagadas automaticamente** do banco — ficam órfãs, sem afetar o funcionamento, mas exigem limpeza manual (`ALTER TABLE ... DROP COLUMN`) se desejado.
- **Fuso horário:** o timestamp de cada avaliação (`dataHora`) é gravado explicitamente em `America/Sao_Paulo`, para não depender do fuso do servidor onde a API está hospedada (Render normalmente roda em UTC).
- **CORS:** liberado para qualquer origem (`*`) nas configurações atuais — adequado enquanto o frontend não tem domínio fixo definitivo. Deve ser restringido ao domínio final assim que a Padá Padoca definir um domínio próprio.

## Autor

Desenvolvido por [Nicholas Jayden](https://github.com/nicholasjayden08) para a Padá Padoca.