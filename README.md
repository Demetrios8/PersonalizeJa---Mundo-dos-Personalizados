# Personalize Já

## Sobre o projeto

O **Personalize Já** é um sistema de gerenciamento desenvolvido para um ateliê de produtos personalizados. Seu objetivo é centralizar as informações de clientes, pedidos, produção, pagamentos e estoque, reduzindo falhas de comunicação, retrabalho e atrasos nas entregas.

Com ele, é possível acompanhar os pedidos desde o atendimento inicial até a conclusão e a entrega do produto.

## Equipe

- Demétrios
- Gabriel Mendes
- Gabriel Marcos
- Keitel Werner
- Italo Ricardo

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok

## Entidades do sistema

| Entidade | Responsabilidade |
|---|---|
| Cliente | Dados de contato e cadastro de quem faz os pedidos |
| Pedido | Pedido do cliente, com status, canal de origem e itens |
| ItemPedido | Produto, quantidade, preço e descrição da personalização |
| Produto | Produto vendido pelo ateliê |
| Arte | Versões da arte de um produto e sua aprovação |
| Pagamento | Pagamentos de um pedido, com forma e status |
| Material | Material em estoque, com quantidade e estoque mínimo |
| MovimentacaoEstoque | Entradas e saídas de material, com motivo e responsável |
| Usuario | Usuário do sistema, com perfil de acesso |

Enumerações: `StatusPedido`, `CanalOrigem`, `StatusAprovacao`, `StatusPagamento`, `FormaPagamento`, `TipoMovimentacao` e `PerfilUsuario`.

As alterações de estado das entidades são feitas por métodos de domínio (por exemplo, `Pedido.iniciarProducao()` e `Pagamento.aprovar()`), e não por setters genéricos. Esses métodos validam as regras antes de alterar o objeto.

## Diagrama de classes UML

![Diagrama de classes UML do Personalize Já](docs/diagrama-uml-personalize-ja.svg)

Relacionamentos e multiplicidades:

- Cliente 1 — 0..* Pedido
- Pedido 1 ◆— 0..* ItemPedido (composição)
- ItemPedido 0..* → 1 Produto
- Produto 1 ◆— 0..* Arte
- Pagamento 0..* → 1 Pedido
- Material 1 — 0..* MovimentacaoEstoque
- MovimentacaoEstoque 0..* → 1 Usuario

## Arquitetura

### Arquitetura escolhida

O projeto utiliza a **Arquitetura em Camadas**, organizando o sistema de acordo com a responsabilidade de cada componente.

### Justificativa

- **Necessidades do negócio:** o ateliê precisa controlar pedidos, pagamentos, aprovação de artes e estoque. Cada uma dessas áreas tem regras próprias, e separá-las em camadas evita que a lógica de negócio se misture com o acesso ao banco ou com a comunicação HTTP.
- **Complexidade do sistema:** o sistema tem complexidade média. Há regras de domínio, como o fluxo de status do pedido (pendente, em produção, concluído, entregue) e a validação de estoque, que devem ficar concentradas em um único lugar (Model e Service) para serem fáceis de manter e testar.
- **Características da equipe:** a equipe tem 5 integrantes que trabalham ao mesmo tempo. Com as camadas bem definidas, cada integrante pode atuar em uma parte do sistema sem conflitos. É também uma arquitetura amplamente conhecida e integrada ao Spring Boot, o que facilita o aprendizado e a divisão de tarefas.

Além disso, essa estrutura facilita a manutenção, a correção de erros, a reutilização de componentes, a criação de novas funcionalidades e a realização de testes em diferentes partes da aplicação.

### Organização do código

O código está organizado no pacote base `com.personalizeja`:

```
src/main/java/com/personalizeja
├── PersonalizejaApplication.java
├── config        # configurações da aplicação
├── controller    # recebe e responde às requisições HTTP
├── service       # regras de negócio
├── repository    # acesso ao banco de dados (Spring Data JPA)
├── model         # entidades JPA e seus métodos de domínio
├── dto           # objetos de transferência de dados entre as camadas
├── mapper        # conversão entre entidades e DTOs
├── enums         # valores fixos usados pelo sistema
└── exception     # tratamento de exceções
```

Nesta etapa, o foco é a modelagem do domínio. Por isso, as camadas `controller`, `service`, `repository`, `dto`, `mapper`, `config` e `exception` já existem na estrutura, mas serão implementadas nas próximas etapas.

## Execução do projeto

Nesta etapa, o foco é a modelagem do domínio (entidades, relacionamentos e arquitetura). Ainda não há controllers, services nem endpoints, e a conexão com o banco de dados PostgreSQL será configurada na próxima etapa. Por isso, por enquanto o projeto é compilado e verificado, e a aplicação com banco ainda não é executada.

### Pré-requisitos

- Java 21
- Maven (ou o Maven Wrapper incluído no projeto)

### Como compilar o projeto

1. Clone o repositório:

   ```bash
   git clone https://github.com/Demetrios8/PersonalizeJa---Mundo-dos-Personalizados.git
   cd personalizeja
   ```

2. Compile o projeto:

   ```bash
   ./mvnw clean compile
   ```

   No Windows, use `mvnw.cmd clean compile`.

Se a compilação terminar com `BUILD SUCCESS`, todas as entidades e enumerações estão corretas.

### Próximas etapas

- Configuração da conexão com o PostgreSQL.
- Implementação das camadas `repository`, `service` e `controller`.

## Status do projeto

Projeto acadêmico em desenvolvimento, realizado como atividade da disciplina de Back-end.