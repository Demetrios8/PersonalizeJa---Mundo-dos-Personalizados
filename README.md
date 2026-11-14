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

## Arquitetura

O projeto utiliza **Arquitetura em Camadas**, separando as responsabilidades da aplicação para facilitar a organização, manutenção e evolução do código.

A estrutura está organizada nas seguintes camadas e pacotes:

- `controller`: comunicação e requisições HTTP.
- `service`: regras de negócio.
- `repository`: acesso e persistência dos dados.
- `model`: entidades do sistema.
- `dto`: objetos de transferência de dados.
- `mapper`: conversão entre DTOs e entidades.
- `enums`: enumerações utilizadas no sistema.
- `exception`: tratamento de exceções.
- `config`: configurações da aplicação.

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
