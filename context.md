# Atividade Prática: API REST do Aurora Shop

**Tecnologias:** Java, Maven, Spring Web, Spring Data JPA, MySQL e Spring Security

## 1. Contexto

A **Aurora Shop** é uma loja virtual fictícia que vende produtos de tecnologia e papelaria. A empresa precisa de uma API REST que permita cadastrar produtos e categorias, registrar clientes e processar pedidos. Você é o(a) desenvolvedor(a) back-end responsável por construir essa API do zero.

Não existe front-end nesta atividade. Os testes serão feitos com Postman (ou ferramenta equivalente).

## 2. Objetivos de aprendizagem

Ao final, você deverá ser capaz de:

- Modelar entidades JPA com relacionamentos `@OneToMany`, `@ManyToOne`, `@ManyToMany` e `@OneToOne`
- Explicar a diferença entre o lado dono e o lado inverso de um relacionamento (`mappedBy`)
- Organizar a aplicação em camadas (Controller, Service, Repository)
- Usar DTOs para não expor entidades diretamente
- Implementar regras de negócio na camada de serviço
- Tratar erros de forma centralizada, com respostas HTTP corretas
- Proteger a API com autenticação e autorização 

## 3. Modelo de domínio

### 3.1 Entidades

**Categoria**

- id, nome (único)

**Produto**

- id, nome, descrição, preço, quantidade em estoque, ativo (sim/não)

**Usuario**

- id, nome, e-mail (único), senha, perfil (`ADMIN` ou `CLIENTE`)

**Endereco**

- id, rua, número, cidade, estado, CEP

**Pedido**

- id, data de criação, status, valor total

**ItemPedido**

- id, quantidade, preço unitário no momento da compra

**Pagamento**

- id, método (`PIX`, `CARTAO`, `BOLETO`), valor, data, status

### 3.2 Relacionamentos

| Relação | Tipo | Observação |
| --- | --- | --- |
| Produto ↔ Categoria | N:N | Um produto pode estar em várias categorias |
| Usuario → Endereco | 1:N | Um usuário pode ter vários endereços |
| Usuario → Pedido | 1:N | Um usuário faz vários pedidos |
| Pedido → ItemPedido | 1:N | Itens não existem sem o pedido |
| ItemPedido → Produto | N:1 | O item referencia um produto |
| Pedido → Pagamento | 1:1 | Pagamento é criado depois do pedido |
| Pedido → Endereco | N:1 | Endereço de entrega |

### 3.3 Status do pedido

`AGUARDANDO_PAGAMENTO` → `PAGO` → `ENVIADO` → `ENTREGUE`

Um pedido também pode ir para `CANCELADO`, mas somente enquanto estiver `AGUARDANDO_PAGAMENTO` ou `PAGO`.

## 4. Requisitos funcionais

### Módulo Categorias

- Cadastrar, listar, buscar por id, atualizar e excluir categoria
- Não permitir nomes duplicados
- Não permitir excluir categoria que tenha produtos vinculados

### Módulo Produtos

- CRUD completo de produto
- Associar um produto a uma ou mais categorias
- Listar produtos filtrando por categoria
- Buscar produtos por nome (contendo o texto, sem diferenciar maiúsculas)
- Listar com paginação (página e tamanho)
- Em vez de excluir um produto que já foi vendido, marcá-lo como inativo

### Módulo Usuários

- Cadastrar usuário (e-mail único)
- Cadastrar e listar endereços de um usuário
- Buscar usuário por id (a resposta **nunca** deve conter a senha)

### Módulo Pedidos

- Criar pedido informando o usuário, o endereço de entrega e a lista de itens (produto e quantidade)
- Listar pedidos de um usuário
- Buscar pedido por id, incluindo os itens e o valor total
- Atualizar o status do pedido
- Cancelar pedido

### Módulo Pagamentos

- Registrar o pagamento de um pedido
- Consultar o pagamento de um pedido

## 5. Regras de negócio

1. O preço de cada item é copiado do produto **no momento da compra**. Se o preço do produto mudar depois, pedidos antigos não podem ser alterados.
2. O valor total do pedido é a soma de (quantidade × preço unitário) de todos os itens, e é calculado no servidor, nunca informado pelo cliente.
3. Não é possível comprar um produto inativo.
4. Não é possível comprar uma quantidade maior que o estoque disponível.
5. Ao criar o pedido, o estoque dos produtos é reduzido. Ao cancelar, o estoque é devolvido.
6. O pagamento só pode ser registrado para pedidos `AGUARDANDO_PAGAMENTO`, e o valor deve ser igual ao total do pedido. Ao pagar, o pedido passa para `PAGO`.
7. As transições de status seguem a ordem definida em 4.3. Não é permitido pular etapas nem voltar.
8. Um pedido deve ter ao menos um item, e cada quantidade deve ser maior que zero.
9. O endereço de entrega deve pertencer ao usuário que está fazendo o pedido.

## 6. Requisitos técnicos

- Arquitetura em camadas: Controller, Service, Repository. O Controller não acessa o Repository diretamente.
- Usar DTOs de entrada e de saída. Nenhuma entidade JPA deve ser devolvida diretamente no JSON.
- Validações com Bean Validation (`@NotBlank`, `@NotNull`, `@Positive`, `@Email` e similares).
- Tratamento centralizado de exceções com `@RestControllerAdvice`, com exceções próprias (por exemplo, recurso não encontrado, estoque insuficiente, transição de status inválida).
- Códigos HTTP coerentes: 200, 201, 204, 400, 404, 409 e 422, conforme o caso.
- Operações que alteram vários registros (criar e cancelar pedido) devem ser transacionais. Você precisa saber explicar por quê.
- Evitar o problema N+1 na listagem de pedidos (pesquise `JOIN FETCH` ou `@EntityGraph`).
- Banco MySQL, com o schema gerado pelo Hibernate no início. Explique no README o significado do valor de `ddl-auto` que escolheu.

## 7. Etapas

### Fase 1: Catálogo (Categoria e Produto)

Entidades, repositórios, CRUD e o relacionamento N:N. **Entregável:** endpoints testados no Postman e coleção exportada.

### Fase 2: Usuários e endereços

Cadastro, relacionamento 1:N e DTO que não vaza a senha. **Entregável:** endpoints testados.

### Fase 3: Pedidos e itens 

Relacionamentos entre Pedido, ItemPedido, Produto, Usuario e Endereco, com a criação do pedido e o cálculo do total. **Entregável:** fluxo completo de criação de pedido funcionando.

### Fase 4: Regras de negócio, pagamento e tratamento de erros

Estoque, status, pagamento, exceções e códigos HTTP corretos. **Entregável:** todas as regras da seção 6 funcionando, com cada violação retornando um erro claro.

### Fase 5: Spring Security

Só comece depois que as fases 1 a 4 estiverem estáveis.

- Senhas criptografadas (BCrypt)
- Endpoint de cadastro e de login
- Autenticação com JWT
- Autorização por perfil:
  - `ADMIN`: gerencia categorias e produtos, vê todos os pedidos e atualiza status
  - `CLIENTE`: lista produtos, cria pedidos e vê **apenas os próprios** pedidos
- Um cliente que tentar acessar o pedido de outro usuário deve receber `403`

**Entregável:** rotas protegidas e testadas com e sem token.

## 6. Entregáveis finais

1. Repositório no GitHub.
2. `README.md` em inglês, com descrição do projeto, tecnologias, como rodar, variáveis de ambiente e lista de endpoints
3. Diagrama de entidades (pode ser desenhado à mão ou em ferramenta)
4. Coleção do Postman exportada

## 7. Perguntas de reflexão

1. Por que `ItemPedido` é uma entidade própria em vez de uma relação N:N direta entre Pedido e Produto?
2. O que muda entre `LAZY` e `EAGER`, e por que `LAZY` é o padrão recomendado em coleções?
3. O que é o problema N+1 e como você o resolveu?
4. Por que o preço unitário é copiado para o item e não lido do produto?
5. Qual a diferença entre autenticação e autorização no seu projeto?
6. O que o `@Transactional` garante na criação de um pedido?

## 8. Desafios extras (opcionais)

- Testes unitários da camada de serviço com JUnit e Mockito
- Documentação da API com Swagger/OpenAPI
