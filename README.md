# Padá Padoca — Sistema de Satisfação

Projeto completo: pesquisa de satisfação (QR code) + painel administrativo.

## Estrutura

```
padapadoca-projeto/
├── backend/            → API Spring Boot (Java 26 / Spring Boot 4.0.6)
│   └── README.md       → como rodar, endpoints disponíveis
└── frontend/
    ├── pesquisa-satisfacao.html   → página que o cliente abre pelo QR code
    └── painel-admin.html          → painel do dono da padaria
```

## Como rodar tudo localmente

1. **Backend**: abre `backend/` no IntelliJ, roda `PadapadocaApiApplication`
   (sobe em `http://localhost:8081` — veja `backend/README.md` se precisar mudar a porta)
2. **Frontend**: abre `frontend/pesquisa-satisfacao.html` e `frontend/painel-admin.html`
   direto no navegador (duplo clique). Os dois já apontam pra `localhost:8081`.
   - No painel admin, o campo "API" no topo permite trocar a URL sem editar código
     (útil quando for publicar em produção).

## Fluxo completo pra testar

1. Preenche uma avaliação em `pesquisa-satisfacao.html` e envia
2. Abre `painel-admin.html` → aba **Avaliações** → deve aparecer a linha nova
3. Aba **Dashboard** → os gráficos e cards atualizam com base nos dados reais

## Próximos passos (ver também backend/README.md)

- [ ] Ajustes finos de CSS na página do cliente (ficou sob responsabilidade do Nicholas)
- [ ] Autenticação no painel admin antes de publicar pra padaria de verdade
- [ ] Link real do Google Reviews (hoje é placeholder em `pesquisa-satisfacao.html`)
- [ ] Deploy do backend (Render/Railway) + hospedar as páginas HTML
- [ ] Endpoint de "cliques no Google" se quiser aquele card do mockup original
