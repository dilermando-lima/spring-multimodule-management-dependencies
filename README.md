# spring-multimodule-management-dependencies

  * [Sobre](#sobre)
  * [Arquitetura dos projetos](#arquitetura-dos-projetos)
  * [Rodando este projeto localmente](#rodando-este-projeto-localmente)
  * [Como importar um dependencias do  gerenciamento de dependencias nos projetos](#como-importar-um-dependencias-do--gerenciamento-de-dependencias-nos-projetos)
  * [Manutenção e deploy do core-spring-modules](#manutenção-e-deploy-do-core-spring-modules)

## Sobre
Este projeto demonstra em projeto simples de como criar uma stack muilti-módulos de dependências springboot para ser implementados em apis, resolvendo assim as questões abaixo:
  - Gerenciamento das verões das dependencias
  - Padronização da libs e depêndecias da apis de um ecossistema grande de aplicações
  - O controle do número das versões das depednencias e suas hierarquia
  - Remover a complexidade do deploy de um ecossistemas com muitas depências e suas hierarquias


## Arquitetura dos projetos 

Este projeto se resume em uma api [api-spring-example](./api-spring-example) que recebe o projeto que possui todas as dependências do core ( [core-spring-dependencies](./core-spring-modules/core-spring-dependencies) ). O todas as dependencias modulares estão dentro do projeto [core-spring-modules](./core-spring-modules)

```bash
.
├── api-spring-example # api que irá utilizar os módulos do core-spring-modules
│       ├── pom.xml 
│       └── src/main/java/example.example.spring.api
│                           │
│                           ├── Application.java
│                           └── Controller.java
│ 
└── core-spring-modules # projeto que gerencia dependencias modulares
        │
        │ # MÓDULO que padroniza as apis spring boot
        ├── core-module-spring-api-basic 
        │   ├── pom.xml
        │   └── src/main
        │           │  
        │           ├── java/example.example.spring.core.apibasic
        │           │       ├── BootstrapProperties.java
        │           │       ├── ExceptionRest.java
        │           │       ├── ExceptionRestProperties.java
        │           │       ├── InfoEndpoint.java
        │           │       └── PropertyPlaceholderAutoConfigurationDefault.java
        │           │
        │           └── resources/META-INF/spring
        │                       └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
        │ 
        │ # MÓDULO que gerencia as conexões com o banco de dados oracle
        ├── core-module-spring-oracle  
        │   ├── pom.xml
        │   └── src/main
        │           │  
        │           ├── java/example.example.spring.core.oracle
        │           │       ├── DataSourceOracleConfig.java
        │           │       └── DataSourceOracleProperties.java
        │           │
        │           └── resources/META-INF/spring
        │                       └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
        │ 
        │ # MÓDULO que formece as libs para testes unitários
        ├── core-module-spring-api-test 
        │   └── pom.xml
        │ 
        │ # MÓDULO que irá gerenciar todos as dependencias-modulos do core
        ├── core-spring-dependencies 
        │   └── pom.xml
        │ 
        └── pom.xml # pom parent do projeto /core-spring-modules que irá cobrir todos os submodulos do core
```

## Rodando este projeto localmente

> Para rodar este projeto é necessário ter `maven 3.9+` e `java 21` já instalado

```bash

# entrando na pasta do core-spring-dependencies
cd ./core-spring-dependencies

# installando todas as dependencias do core localmente
mvn clean install

# retornando a pasta inicial e entrando no projeto da api que utiliza as dependencias
cd ..
cd ./api-spring-example

# installando todas as dependencias da api que utiliza as dependencias
mvn clean install

# rodando a api localmente
java -jar ./target/api-spring-example-1.0.0-SNAPSHOT.jar

# acesse localhost:8085/swagger

```

> Perceba que rodar a aplicação as configurações de porta, resposta de erro, e etc já configurações herdadas das bibliotecas do core

## Como importar um dependencias do  gerenciamento de dependencias nos projetos

Declare o gerenciamanto de dependencia no `pom.xml` do projeto e qual módulo vc necessita usar

```xml
<!-- referencia das dependencias dos módulos a serem importadas -->
<dependencies>
    <!-- importando apenas o modulo A -->
    <dependency>
        <groupId>example.spring.core</groupId>
        <artifactId>modulo-a</artifactId>
    </dependency>
</dependencies>

<!-- referencia do gerenciamento de dependencia que contem todos os módulos -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>example.spring.core</groupId>
            <artifactId>core-spring-dependencies</artifactId>
            <!-- versão das dependencias -->
            <version>${core-spring-dependencies.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

> Mesmo declarando todo `core-spring-modules` em seu projeto, não é necessário importar todos os módulos, então, declare apenas os módulo que façam sentido para sua aplicação.

## Manutenção e deploy do core-spring-modules

Para mais informações de como adicionar um nova dependencia como sub-módulo do core, alterar as versões de todas as dependencias do core ou realizar o deploy das dependencias para um repositório remoto acesse o [readme do próprio projeto core-spring-modules](./core-spring-modules/README.md)