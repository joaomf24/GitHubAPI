***************************************
		README

� Project: GitHubAPI
� Developed by: Jo�o de Matos Fernandes
� Company: InnoWave Technologies
***************************************

-> Nota importante: 
  No ecr� inicial da aplica��o, existem dois campos para autentica��o no GitHub (username e password). 
  A decis�o para inclus�o destes campos na aplica��o passa pela necessidade desta autentica��o para aceder a informa��es 
de perfis que n�o est�o p�blicas, como por exemplo, o endere�o de e-mail dos utilizadores. 
  Os dados introduzidos n�o s�o extraidos para uso malicioso. 
  O 'username' e a 'password' s�o codificados atrav�s da chamada ao m�todo 'encodeToString()' da classe 'Base64' para 
possibilitar a autentica��o 'Basic' no GitHub.

-> Padr�es de desenho: 
  O padr�o de desenho 'Adapter' foi utilizado na apresenta��o dos 'followers' dos utilizadores numa 'ListView'.