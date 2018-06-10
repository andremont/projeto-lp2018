# projeto-lp2018

Enunciado do Projeto Final

❖	PLATAFORMA DE JOGOS DE BARALHO FRANCÊS

Os jogos de cartas de baralho francês são dos mais populares no mundo. Embora haja indícios de que os jogos de cartas teriam surgido na China juntamente com o papel, há outros que apontam uma origem árabe. De qualquer modo, o baralho foi introduzido na Europa durante o século XIV. E a partir do século XV, o desenvolvimento dos processos de impressão e de fabricação de papel propiciou a popularização do baralho em vários países. 
O baralho francês, constituído por 52 cartas, está dividido em 4 naipes, sendo que cada naipe tem um números/letras e figura associados. Os jogos que utilizam este baralho podem usá-lo todo ou um subconjunto obtendo uma menor quantidade de cartas. 
Assuma que pretendemos replicar a base do baralho, para depois podermos aplicar a qualquer jogo. Podemos basear a base nas várias propriedades classes:

•	Card.java

  •	Existem 4 naipes: paus ♣, ouros ♦, copas ♥ e espadas ♠
  •	Cada naipe tem um número ou letra: A, 2-10 e ainda Valete-Dama-Rei; excetuando as personagens, todas as cartas têm o número de ícones do naipe equivalente ao seu número;
  •	Possui algumas operações básicas, além dos getters e setters;

•	CardDeck.java

  •	Cada baralho tem 52 cartas, todas diferentes de todos os naipes e números
  •	Possui operações básicas, como baralhar e biscar

•	CardGame.java

  •	Cada jogo de cartas tem um nome, um número de cartas e número de jogadores, além de um resultado final;

•	Player.java

  •	Cada jogador tem um nome, uma palavra-passe, um número de pontos, um montante de dinheiro e uma mão de cartas CardDeck;

Existem também 2 ficheiros com informação (users.json) e estatísticas (stats.json) dos jogadores .

❖	TAREFAS

O objetivo principal será criar uma aplicação para gerir o backoffice do jogo; existe uma parte para jogar, mas poderá ser feito o jogo ou a simulação, conforme os alunos optem. Após a modelação em UML serão feitas tarefas modulares; o desenvolvimento da estrutura de dados do jogo que contém os jogadores, cartas e estatísticas dividir-se-á em grupos:

Grupo 1: implementação do módulo de gestão de jogadores

  	Criação da Scene para listar jogadores, pesquisar jogadores, ordenar jogadores e visualizar jogadores individualmente

Grupo 2: implementação do módulo de gestão de estatísticas

  	Criação da Scene para listar cartas mais e menos usadas, melhores jogadores, piores jogadores

Grupo 3: implementação do módulo de gestão de regras

  	Criação da Scene para listar regras, pesquisar regras, ordenar regras e visualizar regra individualmente

Grupo 4: implementação do módulo de jogo Poker

  	Criação da Scene para simular o jogo; deverá ter, entre outras coisas, apostas e simulação do jogador ir jogando cartas, com outras a serem biscadas aleatoriamente
  
Grupo 5: implementação do módulo de jogo Sueca

  	Criação da Scene para simular o jogo; deverá ter, entre outras coisas, pontuação atual e simulação do jogador ir jogando cartas, com outras a serem biscadas aleatoriamente;

Grupo 6: implementação do módulo de jogo do Burro

  	Criação da Scene para simular o jogo; deverá ter, entre outras coisas, pontuação atual e simulação do jogador ir jogando cartas, com outras a serem biscadas aleatoriamente;

Grupo 7: implementação do módulo de jogo do Peixinho/Famílias

  	Criação da Scene para simular o jogo; deverá ter, entre outras coisas, pontuação atual e simulação do jogador ir jogando cartas, com outras a serem biscadas aleatoriamente

Grupo 8: implementação do módulo de jogo Solitário

  	Criação da Scene para simular o jogo; deverá ter, entre outras coisas, tempo e simulação do jogador ir jogando cartas, com outras a serem biscadas aleatoriamente.

O código base está presente em https://github.com/andremont/projeto-lp2018. Os grupos deverão fazer checkout da versão existente e trabalhar dentro da pasta ismt.application.scene na sua tarefa específica. Podem sugerir melhoramentos à estrutura principal a qualquer momento e esperar que o docente as reveja e aceite.
O programa deve permitir que em qualquer momento se possa guardar em ficheiro e/ou base de dados a informação existente em memória sobre as cartas, jogadores e estatísticas registadas. A gravação deve ser feita da forma mais otimizada possível e deve permitir que o estado seja recuperado novamente. Poderá ser aproveitado código dos jogos de cartas existente na Internet, desde que devidamente integrados na base do projeto.


❖	OBJETIVO

Com a realização do Projeto Final pretende-se familiarizar os alunos com o desenvolvimento estruturado de uma aplicação em Java, englobando todas as fases de um projeto de software. Com efeito, os alunos devem ser capazes de compreender e dominar os conceitos e as técnicas fundamentais inerentes à conceção, desenho, implementação, testes, usabilidade e avaliação de aplicações, de complexidade moderada, incluindo competências de modelação da informação e domínio da linguagem Java.
Os alunos são livres de melhorar o programa da forma que entenderem, desde que não o desvirtuem. A qualidade da programação é o ponto essencial. 
A resolução deverá cumprir os seguintes requisitos:
o	Estruturação apropriada, i.e., modularização apropriada em métodos (funções e procedimentos);
o	Nomes de entidades (variáveis, constantes e módulos) apropriados;
o	Todas as opções importantes e/ou extras do programa deverão ser explicados no cabeçalho do programa.
Todas as fases do projeto serão alvo de avaliação pelo que se pretende que sejam executadas. A saber:
1.	Modelação do projeto em UML
2.	Geração do código de classes a partir do UML
3.	Realização de testes unitários sobre as classes e respetivos métodos
4.	Utilização de padrões de programação
5.	Implementação de uma interface gráfica GUI
6.	Colocação do código num repositório de Version Control GitHub
7.	Refactoring do código
Os casos omissos neste enunciado deverão ser esclarecidos junto do docente da unidade curricular.


❖	AVALIAÇÃO

O trabalho será avaliado nas seguintes vertentes:
●	Clareza do código
●	Adequação da estrutura de dados
●	Adequação da estrutura de controlo
●	Funcionamento
●	Execução de cada uma das 8 fases indicadas no ponto anterior
A realização de trabalho extra também será valorizada.


❖	ENTREGA

O projeto final deve ser entregue até ao dia 20-06-2018 para o repositório GitHub; em alternativa poderá ser entregue num único ficheiro Zip. Após a data limite de entrega não será aceite qualquer projeto ou partes do mesmo.


❖	ALGUMAS NOTAS

- Não serão admitidos plágios – trabalhos copiados terão nota ZERO (tanto quem copiou como quem deixou copiar).
- A funcionalidade do programa é muito mais importantes do que os aspetos estéticos.
