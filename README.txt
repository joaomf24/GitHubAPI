***************************************
		README

» Project: GitHubAPI
» Developed by: João de Matos Fernandes
» Company: InnoWave Technologies
***************************************

-> Nota importante: 
  No ecrã inicial da aplicação, existem dois campos para autenticação no GitHub (username e password). 
  A decisão para inclusão destes campos na aplicação passa pela necessidade desta autenticação para aceder a informações 
de perfis que não estão públicas, como por exemplo, o endereço de e-mail dos utilizadores. 
  Os dados introduzidos não são extraidos para uso malicioso. 
  O 'username' e a 'password' são codificados através da chamada ao método 'encodeToString()' da classe 'Base64' para 
possibilitar a autenticação 'Basic' no GitHub.

-> Padrões de desenho: 
  O padrão de desenho 'Adapter' foi utilizado na apresentação dos 'followers' dos utilizadores numa 'ListView'.