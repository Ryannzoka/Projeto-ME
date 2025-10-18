# Minha Entrada

Este é um aplicativo Android desenvolvido para a vaga de estágio em Desenvolvimento Android, com o objetivo de simular um fluxo básico de autenticação e cadastro de usuários, todo o layout e design foi inspirado no site minhaentrada.com.br.

## Funcionalidades

- **Splash Screen personalizada** com logo centralizada e cor principal do projeto.
- **Tela de Login**: autenticação por e-mail ou nome de usuário, validação de campos e feedback de erro.
- **Tela de Cadastro**: criação de nova conta com validação de formato de e-mail e senha mínima.
- **Tela Principal**: exibe dados do usuário logado e permite logout.
- **Persistência local** dos dados do usuário usando `SharedPreferences` (implementado em [`com.example.myapplication.data.UserDataStore`](app/src/main/java/com/example/myapplication/data/UserDataStore.kt)).
- **Navegação entre telas** utilizando Jetpack Compose Navigation.

## Tecnologias e escolhas

- **Kotlin**: linguagem principal do projeto.
- **Jetpack Compose**: para construção das interfaces de usuário de forma declarativa e moderna.
- **Material 3**: tema visual baseado no Material Design 3, com personalização de cores e tipografia.
- **SharedPreferences**: para armazenamento simples dos dados do usuário localmente.
- **Gradle**: gerenciamento de dependências e build.
- **Arquitetura de navegação**: Compose Navigation para fluxo entre telas.
- **Validação de dados**: Expressões regulares para e-mail e regras de senha.
- **Compatibilidade**: minSdk 24, targetSdk 36.

## Estrutura do Projeto

- [`MainActivity`](app/src/main/java/com/example/myapplication/MainActivity.kt): ponto de entrada e controle de navegação.
- [`ui/theme`](app/src/main/java/com/example/myapplication/ui/theme): definição de cores, tipografia e tema.
- [`ui/splash`](app/src/main/java/com/example/myapplication/ui/splash/SplashScreen.kt): tela de splash.
- [`ui/login`](app/src/main/java/com/example/myapplication/ui/login/LoginScreen.kt): tela de login.
- [`ui/cadastro`](app/src/main/java/com/example/myapplication/ui/cadastro/CadastroScreen.kt): tela de cadastro.
- [`ui/main`](app/src/main/java/com/example/myapplication/ui/main/MainScreen.kt): tela principal do usuário.
- [`data/UserDataStore`](app/src/main/java/com/example/myapplication/data/UserDataStore.kt): persistência dos dados do usuário.

## Motivações das escolhas

- **Jetpack Compose** foi escolhido pela facilidade de manutenção, rapidez no desenvolvimento e integração com Material Design.
- **Material 3** para garantir visual moderno e responsivo.
- **SharedPreferences** por ser suficiente para persistência simples em um app de demonstração.
- **Navegação Compose** para facilitar o fluxo entre telas sem boilerplate de Fragments.

## Como rodar

1. Clone o repositório.
2. Abra no Android Studio.
3. Execute em um dispositivo ou emulador Android (API 24+).
