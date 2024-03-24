O projeto foi concebido com conceitos de Architecture
Está dividido em: 
  - features_nickname - Funcionalidades para o Apelido
  - features_posts - Funcionalidades para os post

Cada feature está dividida em:
  - Data - DataSource e Repositório
  - Domain - Model, Interface do Repositório, casos de Uso, e utilidades
  - Presentation - Implementação das Interfaces

A decisão foi feita em função das vantagens desse tipo de implementação que gera baixo acoplamento e divisão de responsabilidades.
Assim, temos um código escalável e bastante testável.

Usa as tecnologias: JetPack Compose, Room (local storage) e Retrofit (remote)
Para injeção de dependência, usa-se Dagger-Hilt

Construído usando Android Studio Iguana - Preferencialmente deverá ser usado essa ferramenta para costrução e teste do aplicativo 
