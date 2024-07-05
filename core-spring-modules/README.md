
# core-spring-modules

  * [Sobre](#sobre)
  * [Descriação dos módulos](#descrição-dos-módulos)
  * [Como importar um módulo no meu projeto](#como-importar-um-módulo-no-meu-projeto)
  * [Como atualizar a versão dos módulos](#como-atualizar-a-versão-dos-módulos)
  * [Realizando um deploy dos módulos](#realizando-um-deploy-dos-módulos)
  * [Como criar um novo módulo](#como-criar-um-novo-módulo)
  * [Como adicionar uma nova dependência externa que será utilizada nos módulos](#como-adicionar-uma-nova-dependência-externa-que-será-utilizada-nos-módulos)


## Sobre

Este projeto é um projeto multi-módulos que contem vários sub projetos core para serem importados em outros projetos, visando apenas dependencias que auxiliam o ecossistema `spring`

## Descrição dos módulos

nome | descrição 
---  | --- 
**core-spring-dependencies** | módulo principal que irá conter todos os demais módulos para serem importados nos projetos e aplicações 
core-module-spring-api-test | módulo que gerencia as libs e implementações para serem usados em testes unitários
core-module-spring-oracle | módulo que configura e gerencia datasources de conexão ao oracle 
core-module-spring-api-basic | módulo que configura os padrões de api's rest do core


## Como importar um módulo no meu projeto

Deplace o gerenciamanto de dependencia no seu projeto e qual módulo vc necessita usar no `pom.xml` do seu projeto

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

## Como atualizar a versão dos módulos

Todos os módulos terão a mesma versão e serão promovidos para uma nova versão juntos.

```bash
# instalando os módulos localmente
mvn clean install

# alterando todos os módulos para a nova versão , em ex: para nova versão 10.0.0-SNAPSHOT
mvn versions:set -DnewVersion="10.0.0-SNAPSHOT" -DgenerateBackupPoms=false

# resolvendo as dependencias dos módulos e instalando localmente  a nova versão alterada nos pom.xml de cara sub-projeto
mvn clean install
```

## Realizando um deploy dos módulos

Todos os módulos serão deployados juntos para o artifactory sendo assim caso haja uma nova versão todos os mmodulos terão uma nova versão crida como artefato no repositório

```bash
# resolvendo as dependencias dos módulos localmente
mvn deploy
```

## Como criar um novo módulo

 - Adicione um novo diretório com o prefixo "core-module-*" ou "core-module-spring" contendo o seu próprio pom.xml
 - No `pom.xml` deste novo modulo criado adicione a referencia ao projeto root/pai `core-spring-modules` pelas tags `<parent></<parent>`
 - Adicione no `pom.xml` raiz deste projeto a referencia do seu novo módulo nas tags `<modules></modules>`
 - Adicione seu novo módulo no projeto/módulo `core-spring-dependencies` pois todos os demais projetos irão importar apenas estes projeto que contem todos os módulos
 - Crie uma nova versão para este projeto e todos os módulos ( lembrando que todos possuem o mesma versão )

## Como adicionar uma nova dependência externa que será utilizada nos módulos

 - Declare a propriedade de versão desta dependencia no arquivo `pom.xml` raiz desse projeto em `<properties>`
 - Declare a referencia do artifactory da dependencia no arquivo `pom.xml` raiz desse projeto em `<dependencyManagement><dependencies>` utilizando a propriedade de versão declarada antes
 - Adicione a dependência no módulo que queira utilizar ( não será necessário informar a versão pois o mesmo já está referenciado no `pom.xml` pai )
 
 