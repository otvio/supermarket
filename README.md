#supermarket
##SSC0103 - Programacão Orientada a Objetos - Prof. Adenilso da Silva Simão - Trabalho 4

###        ...:::  LORMarket  :::...

Membros do Grupo: nº USP

- Laércio de Oliveira Júnior        8922198       

- Luis Ricardo Guabiraba da Silva   8937402

- Otávio Augusto de Oliveira        8936801

Professor:

- Adenilso Simao   -  Programação Orientada a Objetos (P.O.O).    

O sistema foi implementado usando o ambiente Netbeans IDE 8.0.2 com Java 7.

####Obs: versão pronta em "Releases".

####Como executar:

    1 - Descompacte o zip para a pasta desejada.

    2 - No Netbeans, abra o projeto "SuperMarket".    

    3 - Aperte Shift-F11 para limpar e construir o projeto.

	- Para executar o servidor, vá até o package "server", e após isso, clique com o botão direito do mouse
	na classe Server, e logo após isso, escolha a opção "Executar arquivo".
	- Para executar a aplicação do client, vá até o package "client", e após isso, clique com o botão direito
	do mouse na classe Client, e escolha a opção "Executar arquivo".

    4 - Ver (início do programa).


####Início do programa:
	
	Inicialmente o usuário terá a opção de escolher a aplicação que será executada, servidor ou cliente.

Servidor:

	1 - Logo após inicializado o servidor, pode-se observar o menu do servirdor, com 7 opções, (1) registrar novos
	produtos, (2) listar todos os produtos, (3) registrar novos fornecedores, (4) adicionar uma nova categoria, (5)
	gerar um pdf das vendas, podendo optar por escolher as vendas do dia ou do mês, (6) finalizar aplicação do servidor.


Cliente:
	
	1 - Primeiramente será solicitado para o usuário escolher entre criar um novo usuário (1), ou logar com um
	usuário existente(2). Caso o usuário escolha a opção 1, irá criar um novo usuário, ou caso a opção escolhida
	seja (2), ai terá que entrar com um login e uma senha.

	2 - Quando esta etapa terminar, o usuário estará logado e pronto para utilizar o sistema, e temos 5
	opções de escolha. (1) lista todos os produtos do supermercado, (2) lista todos os produtos na lista de desejos,
	(3) adiciona um produto na lista de desejos, (4) realizar a compra de um produto, e (5) para sair do programa.

	3 - Caso o usuário escolha a opção (3), serão listados todos os produtos, e o usuário poderá escolher o código
	do produto para ser adicionado a sua lista de desejos.

	4 - Caso o usuário escolha a opção (4), serão listados todos os produtos, e o usuário poderá escolher o código
	do produto que será comprado, e logo após isso, ele deverá escolher a quantidade de unidades a ser adquirida.

####Extras:

#####Gerador de PDF:

  Para a criação de relatórios de vendas em pdf utilizamos a biblioteca "iText - 5.0.5", aonde apresentamos as vendas feitas em um intervalo de tempo selecionado pela aplicação servidor, e no final apresentamos umas estatísticas básicas sobre as vendas.
  
#####Email:

  A notificação de produtos indisponíveis é realizada através da JavaMail API, no momento em que o estoque é abastecido.
  
#####Design Patterns:

  O padrão utilizado foi o Singleton Pattern. Foi utilizado nas classes "server.Server" e na "server.ClientConnection", para poder limitar a quantidade de instancias do objeto, pois do modo que o sistema foi modelado é preferível que haja apenas uma instancia das classes Server e ClientConnection.
  


