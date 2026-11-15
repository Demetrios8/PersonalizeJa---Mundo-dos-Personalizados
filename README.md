# Personalize Já

## Sobre o projeto

O **Personalize Já** é um sistema de gerenciamento desenvolvido para um ateliê de produtos personalizados. Seu objetivo é centralizar as informações de clientes, pedidos, produção, pagamentos e estoque, reduzindo falhas de comunicação, retrabalho e atrasos nas entregas.

## Objetivo

Desenvolver uma aplicação que facilite o gerenciamento das atividades do ateliê, permitindo acompanhar os pedidos desde o atendimento inicial até a conclusão e entrega do produto.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok


# Documentação da Arquitetura - Personalize Já

## 1. Arquitetura utilizada

O projeto Personalize Já utiliza a Arquitetura em Camadas,
organizando o sistema de acordo com as responsabilidades
de cada componente.

Essa abordagem separa as regras de negócio, a comunicação
com o usuário e o acesso aos dados, facilitando a manutenção
e a evolução da aplicação.

## 2. Organização das camadas

- Controller: recebe e responde às requisições HTTP.
- Service: concentra as regras de negócio da aplicação.
- Repository: realiza operações de acesso ao banco de dados.
- Model: contém as entidades que representam os dados
  do sistema.
- DTO: transporta informações entre as camadas.
- Mapper: realiza conversões entre entidades e DTOs.
- Enums: define valores fixos utilizados pelo sistema.
- Exception: concentra o tratamento de exceções.
- Config: armazena configurações da aplicação.

## 3. Justificativa da escolha

A Arquitetura em Camadas foi escolhida por proporcionar
uma organização clara do código e separar as
responsabilidades do sistema.

Essa estrutura facilita:

- A manutenção do código.
- A identificação e correção de erros.
- A reutilização de componentes.
- A implementação de novas funcionalidades.
- O trabalho simultâneo entre os integrantes da equipe.
- A realização de testes em diferentes partes da aplicação.

## 4. Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok

## 5. Organização da responsabilidade de pagamentos

A funcionalidade de pagamentos está representada pela
entidade Pagamento, responsável por armazenar o valor,
a forma de pagamento, o status e a data do registro.

As enumerações FormaPagamento e StatusPagamento
padronizam os valores aceitos pelo sistema.

O relacionamento entre Pedido e Pagamento utiliza
ManyToOne na entidade Pagamento, permitindo que um
pedido possua múltiplos registros de pagamento.

------------------------------------------------

## Equipe

- Demétrios
- Gabriel Mendes
- Gabriel Marcos
- Keitel Werner
- Italo Ricardo

## Execução do projeto

**Pré-requisitos:**
- Java 21 instalado.
- Maven instalado ou Maven Wrapper disponível.
- PostgreSQL configurado.

As configurações de conexão com o banco de dados serão documentadas conforme a configuração do ambiente de desenvolvimento for concluída.

## Status do projeto

Projeto acadêmico em desenvolvimento, realizado como atividade da disciplina de Back-end.
