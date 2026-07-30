# padapadoca-api

Backend da pesquisa de satisfação da Padá Padoca. Recebe as avaliações enviadas
pela página do QR code e alimenta o futuro painel administrativo.

## Como rodar

1. Abra a pasta no IntelliJ (File > Open, selecione a pasta `padapadoca-api`)
2. Deixe o Maven baixar as dependências
3. Rode a classe `PadapadocaApiApplication`
4. A API sobe em `http://localhost:8080`

O banco (H2) é criado automaticamente em `./data/padapadoca.mv.db` na primeira
execução — nada pra configurar.

## Endpoints

| Método | Rota                        | O que faz                                            |
|--------|------------------------------|-------------------------------------------------------|
| POST   | `/api/avaliacoes`             | Cria uma nova avaliação (usado pela página do cliente) |
| GET    | `/api/avaliacoes`              | Lista avaliações, paginado (`?page=0&size=20`)         |
| GET    | `/api/avaliacoes/estatisticas` | Retorna médias e totais pro dashboard                  |

### Exemplo de POST

```json
POST /api/avaliacoes
{
  "notaGeral": 5,
  "notaAtendimento": 5,
  "notaProdutos": 4,
  "notaAmbiente": 5,
  "comentario": "Pão de queijo excelente!",
  "nomeCliente": "Maria"
}
```

## Próximos passos

- [ ] **Segurança**: hoje `GET /api/avaliacoes` e `/api/avaliacoes/estatisticas` estão
      abertos. Antes de ir pra produção, adicionar Spring Security com login simples
      pro dono da padaria acessar o painel.
- [ ] **Deploy**: escolher onde hospedar (Render, Railway, ou uma VPS simples) e trocar
      o H2 por PostgreSQL se o volume de avaliações crescer.
- [ ] **Frontend do cliente**: trocar o `fetch` comentado em `pesquisa-satisfacao.html`
      pela URL real da API assim que estiver publicada.
- [ ] **Painel administrativo**: construir consumindo `GET /api/avaliacoes` e
      `GET /api/avaliacoes/estatisticas`.
