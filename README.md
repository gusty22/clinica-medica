# Clinica Médica - Projeto Integrador

O **Clinica Médica** é um projeto desenvolvido como parte do **Projeto Integrador** da faculdade, com o objetivo de aplicar conceitos de desenvolvimento de software, arquitetura de microsserviços e boas práticas de engenharia em um sistema realista para gerenciamento de clínicas médicas. Este sistema foi concebido para atender às necessidades de uma clínica, oferecendo funcionalidades como cadastro de funcionários, pacientes, convênios e especialidades, agendamento de consultas e gerenciamento de prontuários médicos. O projeto é uma vitrine do aprendizado adquirido em disciplinas como Programação, Banco de Dados, Engenharia de Software e Arquitetura de Sistemas, demonstrando a capacidade de criar soluções escaláveis e robustas.

## Visão Geral

O **Clinica Médica** é mais do que um simples sistema: é uma prova de conceito que une tecnologia moderna, trabalho em equipe e resolução de problemas reais. Ele foi projetado com uma arquitetura de microsserviços, permitindo modularidade e escalabilidade, e utiliza tecnologias de ponta para garantir desempenho e manutenibilidade. Cada módulo foi cuidadosamente planejado para simular o funcionamento de uma clínica médica, com foco em usabilidade, segurança de dados e eficiência operacional.

### Objetivos do Projeto
- **Educacional**: Consolidar conhecimentos adquiridos na graduação, como desenvolvimento Java, persistência de dados, APIs REST e gerenciamento de projetos.
- **Prático**: Criar uma solução funcional que simule operações reais de uma clínica médica.
- **Colaborativo**: Promover o trabalho em equipe, com divisão de responsabilidades entre os membros do grupo.
- **Inovador**: Explorar uma arquitetura de microsserviços, preparando os alunos para tendências modernas de desenvolvimento.

## Estrutura do Projeto

O projeto é estruturado como um projeto Maven multi-módulo, refletindo a complexidade e a organização de sistemas corporativos:

- **clinica-medica**: Módulo pai que centraliza configurações globais, dependências e plugins, garantindo consistência entre os microsserviços.
- **clinica-medica-comum**: Módulo compartilhado com entidades, DTOs, repositórios e serviços que formam a base para os outros módulos.
- **clinica-medica-administrativo**: Microsserviço focado em operações administrativas, como gerenciamento de funcionários, perfis, especialidades e convênios.
- **clinica-medica-agendamento**: Microsserviço dedicado ao agendamento de consultas, com validações de conflitos de horário.
- **clinica-medica-atendimento**: Microsserviço para gerenciamento de prontuários médicos, essencial para o acompanhamento clínico.

## Pré-requisitos

Para executar o projeto, você precisará de:

- **Java 17** ou superior (o coração do nosso sistema, garantindo compatibilidade com as bibliotecas mais recentes).
- **Maven 3.8.0** ou superior (para gerenciar dependências e builds com estilo).
- **MySQL 8.0** ou superior (nosso banco de dados relacional, robusto e confiável).
- **IDE** (recomendamos IntelliJ IDEA ou Eclipse, porque quem não gosta de um bom editor de código?).
- Conexão com um banco de dados MySQL configurado (não se preocupe, vamos te guiar!).

## Tecnologias Utilizadas

O projeto brilha com um conjunto de tecnologias modernas, escolhidas para maximizar a produtividade e a qualidade do código:

- **Spring Boot 3.2.5**: O framework que torna tudo mais fácil, desde APIs REST até injeção de dependências.
- **Spring Data JPA**: Para interagir com o banco de dados de forma elegante e eficiente.
- **MySQL Connector 8.0.33**: Conecta nosso sistema ao MySQL com performance e confiabilidade.
- **Lombok 1.18.30**: Porque ninguém merece escrever getters e setters manualmente.
- **Springdoc OpenAPI 2.3.0**: Gera uma documentação Swagger interativa, perfeita para explorar a API.
- **ModelMapper 3.2.0**: Facilita o mapeamento entre entidades e DTOs, mantendo o código limpo.
- **Hibernate**: Para mapear objetos para o banco de dados com precisão cirúrgica.
- **Jakarta Validation**: Validações robustas para garantir dados consistentes.

## Configuração do Ambiente

Siga estas etapas para transformar o código em um sistema vivo e funcional:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/gusty22/clinica-medica.git
   cd clinica-medica
