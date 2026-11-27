# Personalize Já

## Sobre o projeto

O **Personalize Já** é um sistema de gerenciamento desenvolvido para um ateliê de produtos personalizados. Seu objetivo é centralizar as informações de clientes, produtos, pedidos, produção, pagamentos e estoque, reduzindo falhas de comunicação, retrabalho e atrasos nas entregas.

O sistema permite acompanhar os pedidos desde o atendimento inicial até a conclusão e a entrega do produto, além de controlar a aprovação das artes, os pagamentos e a movimentação dos materiais utilizados na produção.

## Equipe

- Demétrios
- Gabriel Mendes
- Gabriel Marcos
- Keitel Werner
- Italo Ricardo

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Bean Validation
- H2 Database (banco em memória para desenvolvimento)
- PostgreSQL (dependência incluída; conexão prevista para uma próxima etapa)
- Maven
- Lombok

## Funcionalidades do sistema

- **Clientes:** cadastro, consulta, listagem e atualização dos dados dos clientes.
- **Produtos:** cadastro, consulta, listagem, atualização e ativação ou desativação dos produtos.
- **Pedidos:** criação e acompanhamento dos pedidos, incluindo status, canal de origem, cliente e itens.
- **Personalização e artes:** envio e consulta de artes vinculadas aos produtos, com aprovação, reprovação e solicitação de revisão. O início da produção de um pedido depende da aprovação de suas artes.
- **Pagamentos:** cadastro, consulta, atualização e controle dos status dos pagamentos, incluindo aprovação, recusa, cancelamento e estorno.
- **Estoque:** cadastro e gerenciamento de materiais, consulta de quantidade e identificação de materiais abaixo do estoque mínimo.
- **Movimentações de estoque:** registro de entradas e saídas de materiais, com quantidade, motivo e identificação do usuário responsável. O sistema valida as movimentações para evitar estoque negativo.
- **Usuários:** cadastro, consulta, atualização de dados, alteração de senha e perfil, além de ativação e desativação.

## Entidades do sistema

| Entidade | Responsabilidade |
|---|---|
| Cliente | Dados de contato e cadastro de quem faz os pedidos |
| Pedido | Pedido do cliente, com status, canal de origem, itens e artes |
| ItemPedido | Produto, quantidade, preço e descrição da personalização |
| Produto | Produto oferecido pelo ateliê |
| Arte | Arquivo, versão e processo de aprovação da arte de um produto; pode estar associada a um pedido |
| Pagamento | Pagamento associado a um pedido, com forma e status |
| Material | Material em estoque, com quantidade e estoque mínimo |
| MovimentacaoEstoque | Entradas e saídas de materiais, com motivo e usuário responsável |
| Usuario | Usuário do sistema, com perfil de acesso e situação de ativação |

### Enumerações

O projeto utiliza as seguintes enumerações:

- `StatusPedido`
- `CanalOrigem`
- `StatusAprovacao`
- `StatusPagamento`
- `FormaPagamento`
- `TipoMovimentacao`
- `PerfilUsuario`

As alterações de estado das entidades são realizadas por métodos de domínio, como `Pedido.iniciarProducao()`, `Pagamento.aprovar()` e `Usuario.desativar()`. Assim, as regras de negócio ficam concentradas nas entidades e nos serviços responsáveis pelas operações, evitando alterações indevidas por meio de setters genéricos.

## Diagrama de classes UML

O diagrama UML apresenta as entidades do sistema, seus atributos, métodos e relacionamentos, incluindo as respectivas multiplicidades.

![Diagrama de classes UML do Personalize Já](docs/diagrama-uml-personalize-ja.svg)

### Relacionamentos e multiplicidades

- Cliente 1 — 0..* Pedido
- Pedido 1 ◆— 0..* ItemPedido (composição)
- Produto 1 — 0..* ItemPedido
- Produto 1 — 0..* Arte
- Pedido 0..1 — 0..* Arte
- Pedido 1 — 0..* Pagamento
- Material 1 — 0..* MovimentacaoEstoque
- Usuario 1 — 0..* MovimentacaoEstoque

## Arquitetura

### Organização adotada

O projeto utiliza a **Arquitetura em Camadas**, com os pacotes organizados principalmente por funcionalidade (módulos de domínio). Dessa forma, cada área do sistema possui seus próprios componentes, separados conforme suas responsabilidades, como `controller`, `service`, `repository`, `entity`, `dto` e `mapper`, quando necessários.

Essa organização mantém próximas as classes que trabalham com a mesma funcionalidade e separa a comunicação HTTP, as regras de negócio, o acesso aos dados e a transferência de informações.

### Justificativa

- **Necessidades do negócio:** o ateliê precisa controlar clientes, produtos, pedidos, pagamentos, aprovação de artes e estoque. A separação das responsabilidades evita que as regras de negócio fiquem misturadas com a comunicação HTTP ou com o acesso ao banco de dados.
- **Complexidade do sistema:** o sistema possui complexidade média, pois envolve diferentes entidades, relacionamentos e regras de negócio, como o fluxo de status dos pedidos, a aprovação de artes e a movimentação do estoque. A divisão por funcionalidades facilita localizar e manter cada parte.
- **Características da equipe:** a equipe possui cinco integrantes. A organização por módulos facilita a divisão do trabalho por áreas do sistema, enquanto a separação em camadas dentro dos módulos mantém um padrão de desenvolvimento.

Essa estrutura também facilita a manutenção, a correção de erros, a reutilização de componentes e a realização de testes.

### Organização do código

O código-fonte está no pacote base `com.personalizeja`, organizado por funcionalidades:

```text
src/main/java/com/personalizeja
├── PersonalizejaApplication.java
├── arte/
│   ├── dto/
│   ├── entity/
│   ├── enums/
│   ├── repository/
│   └── service/
├── cliente/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── mapper/
│   ├── repository/
│   └── service/
├── config/
├── exception/
├── material/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── mapper/
│   ├── repository/
│   └── service/
├── movimentacaoestoque/
│   ├── dto/
│   ├── entity/
│   ├── enums/
│   ├── mapper/
│   └── repository/
├── pagamento/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── enums/
│   ├── mapper/
│   ├── repository/
│   └── service/
├── pedido/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── enums/
│   ├── mapper/
│   ├── repository/
│   └── service/
├── produto/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── mapper/
│   ├── repository/
│   └── service/
└── usuario/
    ├── controller/
    ├── dto/
    ├── entity/
    ├── enums/
    ├── mapper/
    ├── repository/
    └── service/
```

As principais responsabilidades são:

- **Controller:** recebe as requisições HTTP e encaminha as operações para os serviços. Os endpoints de artes são disponibilizados pelo `ProdutoController`, e as movimentações de estoque são realizadas pelo `MaterialController`.
- **Service:** concentra as regras de negócio e coordena as operações de cada funcionalidade.
- **Repository:** realiza o acesso e a persistência dos dados utilizando Spring Data JPA.
- **Entity:** representa as entidades persistidas no banco de dados, seus atributos, relacionamentos e métodos de domínio.
- **DTO:** define os objetos utilizados para receber dados das requisições e retornar informações da API.
- **Mapper:** realiza a conversão entre entidades e DTOs.
- **Enums:** define os valores fixos utilizados pelo sistema.
- **Exception:** reúne exceções próprias e o tratamento global de erros.
- **Config:** pacote destinado às configurações gerais da aplicação.

Nem todos os módulos possuem exatamente os mesmos subpacotes. Os componentes são organizados conforme a necessidade de cada funcionalidade.

## Execução do projeto

### Pré-requisitos

- Java 21
- Maven ou Maven Wrapper incluído no projeto

Não é necessário instalar o PostgreSQL para executar a configuração atual, pois o projeto utiliza o H2 em memória durante o desenvolvimento.

### Como obter o projeto

```bash
git clone https://github.com/Demetrios8/PersonalizeJa---Mundo-dos-Personalizados.git
cd PersonalizeJa---Mundo-dos-Personalizados
```

### Como compilar o projeto

No Linux ou macOS:

```bash
./mvnw clean compile
```

No Windows:

```bash
mvnw.cmd clean compile
```

### Como executar o projeto

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação ficará disponível em `http://localhost:8081`.

### Banco de dados H2

A configuração atual utiliza o **H2 em memória**, definido em `src/main/resources/application.yml`. As tabelas são criadas automaticamente a partir das entidades quando a aplicação inicia. Como o banco é em memória, os dados são perdidos quando a aplicação é encerrada.

O console do H2 pode ser acessado em `http://localhost:8081/h2-console`, utilizando os seguintes dados:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:testdb` |
| Usuário | `sa` |
| Senha | (vazia) |

A dependência do PostgreSQL já está incluída no projeto, mas a conexão com esse banco ainda não está configurada no arquivo de propriedades.

## Endpoints da API

Os endpoints utilizam o prefixo `/api`.

| Recurso | Endpoints disponíveis |
|---|---|
| Clientes (`/api/clientes`) | `POST`, `GET`, `GET /{id}`, `PUT /{id}` |
| Usuários (`/api/usuarios`) | `POST`, `GET`, `GET /{id}`, `PUT /{id}`, `PATCH /{id}/senha`, `PATCH /{id}/perfil`, `PATCH /{id}/ativar`, `PATCH /{id}/desativar` |
| Produtos (`/api/produtos`) | `POST`, `GET`, `GET /{id}`, `PUT /{id}`, `PATCH /{id}/ativar`, `PATCH /{id}/desativar` |
| Artes | `POST /api/produtos/{id}/artes`, `GET /api/produtos/{id}/artes`, `PATCH /api/artes/{id}/aprovar`, `PATCH /api/artes/{id}/reprovar`, `PATCH /api/artes/{id}/revisao` |
| Pedidos (`/api/pedidos`) | `POST`, `GET`, `GET /{id}`, `PATCH /{id}/iniciar-producao`, `PATCH /{id}/concluir`, `PATCH /{id}/entregar`, `PATCH /{id}/cancelar` |
| Pagamentos (`/api/pagamentos`) | `POST`, `GET`, `GET /{id}`, `PUT /{id}`, `PATCH /{id}/aprovar`, `PATCH /{id}/recusar`, `PATCH /{id}/cancelar`, `PATCH /{id}/estornar` |
| Materiais (`/api/materiais`) | `POST`, `GET`, `GET /{id}`, `PUT /{id}`, `GET /abaixo-do-minimo`, `POST /{id}/entradas`, `POST /{id}/saidas` |

As operações de entrada e saída de estoque retornam os dados da movimentação registrada. Elas são disponibilizadas dentro do recurso de materiais.

## Próximas etapas

- Ampliar os testes das regras de negócio e dos endpoints.
- Configurar e validar a conexão com PostgreSQL.
- Implementar autenticação e armazenamento seguro de senhas.
- Realizar ajustes e melhorias identificados durante os testes.

## Status do projeto

Projeto acadêmico em desenvolvimento, realizado como atividade da disciplina de Back-end, utilizando Java e Spring Boot. A aplicação já possui entidades, regras de negócio, persistência com H2 e endpoints REST para os principais recursos do sistema.
