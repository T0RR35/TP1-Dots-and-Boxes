# TP1 - Dots and Boxes

Este trabalho prático consiste em um remake do jogo "Dots and Boxes", desenvolvido em Java 17 com a biblioteca libGDX na versão 1.13.1, para a matéria de LLP2 do curso de informática do CEFET-MG campus Contagem. 

O desenvolvimento foi realizado utilizando o Visual Studio Code (versão 1.100.2) como IDE, e o GitHub foi utilizado para controle de versão e organização do projeto entre os membros da equipe.


## Integrantes da equipe
- [@AninhaCampos](https://github.com/AninhaCampos)
- [@Pedro867](https://github.com/Pedro8675)
- [@T0RR35](https://github.com/T0RR35)

## Plataformas

- `core`: Módulo principal com a lógica da aplicação compartilhada por todas as plataformas.
- `lwjgl3`: Plataforma desktop primária usando LWJGL3; era chamada de 'desktop' em documentações antigas.

## Gradle

Este projeto utiliza [Gradle](https://gradle.org/) para gerenciar as dependências. O wrapper do Gradle foi incluído, então você pode executar tarefas Gradle usando os comandos `gradlew.bat` (Windows) ou `./gradlew` (Linux/macOS).

### Tarefas e Flags Úteis do Gradle

- `--continue`: Quando usada, erros não interromperão a execução das tarefas.
- `--daemon`: Com esta flag, o daemon do Gradle será usado para executar as tarefas escolhidas.
- `--offline`: Utiliza arquivos de dependências em cache.
- `--refresh-dependencies`: Força a validação de todas as dependências. Útil para versões snapshot.
- `build`: Compila os fontes e cria os arquivos de cada projeto.
- `cleanEclipse`: Remove dados de projeto do Eclipse.
- `cleanIdea`: Remove dados de projeto do IntelliJ.
- `clean`: Remove as pastas `build`, que armazenam classes compiladas e arquivos construídos.
- `eclipse`: Gera dados de projeto do Eclipse.
- `idea`: Gera dados de projeto do IntelliJ.
- `lwjgl3:jar`: Constrói o arquivo JAR executável da aplicação, que pode ser encontrado em `lwjgl3/build/libs`.
- `lwjgl3:run`: Inicia a aplicação.
- `test`: Executa os testes de unidade (se houver).

A maioria das tarefas que não são específicas de um único projeto podem ser executadas com o prefixo `nome:`, onde `nome` deve ser substituído pelo ID de um projeto específico. Por exemplo, `core:clean` remove a pasta `build` apenas do projeto `core`.

## Como Baixar, Compilar e Rodar o Jogo

### 1. Baixar o Projeto

Para baixar o projeto, você pode clonar o repositório do GitHub utilizando o seguinte comando:

```bash
git clone <URL_DO_SEU_REPOSITORIO>
```

Substitua `<URL_DO_SEU_REPOSITORIO>` pela URL real do seu repositório GitHub.

### 2. Abrir o Projeto na IDE

Após baixar o projeto, abra-o na sua IDE de preferência (Visual Studio Code, IntelliJ IDEA ou Eclipse). O Gradle deve ser automaticamente reconhecido e as dependências baixadas.

### 3. Compilar o Projeto

Para compilar o projeto, navegue até a raiz do projeto no terminal e execute o comando Gradle de build:

```bash
./gradlew build
```
ou no Windows:
```bash
gradlew.bat build
```

Este comando irá compilar todos os módulos do projeto e gerar os artefatos necessários.

### 4. Rodar o Jogo

Para rodar o jogo diretamente da IDE ou via linha de comando, utilize a seguinte tarefa Gradle:

```bash
./gradlew lwjgl3:run
```
ou no Windows:
```bash
gradlew.bat lwjgl3:run
```

Este comando iniciará a aplicação desktop do jogo "Dots and Boxes".

### 5. Gerar o JAR Executável

Se você deseja um arquivo JAR executável para distribuir o jogo, use o seguinte comando:

```bash
./gradlew lwjgl3:jar
```
ou no Windows:
```bash
gradlew.bat lwjgl3:jar
```

O arquivo JAR será gerado na pasta `lwjgl3/build/libs`.
