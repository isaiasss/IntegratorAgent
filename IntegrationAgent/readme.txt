Instalação

1-Realizar checkout deste projeto
2-Carregar as dependências (Gson e Log4J) através do Maven
3-Configurar os arquivos de configuração (db_config.xml e integration.xml)
4-Adicionar o JDBC do banco de dados escolhido:
-- SQL_SERVER (https://docs.microsoft.com/pt-br/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15)
-- PostgreSQL (https://jdbc.postgresql.org/download.html)
-- Oracle (https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html)
5-Rodar a classe Main (com.we3.integrador.Main) através de comando ou iniciar como serviço

Processo

* Cada query executada, os valores lidos serão enviados para a API configurada
* Logs serão gerados em cada execução ficando no arquivo app.log

Dependências

-Gson
-Apache Log4J
-Driver JDBC
-Java JRE 1.8 / Java 8

Configuração

Exemplo de arquivo integration.xml:

<integration>
	<procedure>
		<code>1</code> <!-- codigo identificador -->
		<name>SELECAO DE PESSOAS</name> <!-- nome para query -->
		<query> <!-- query -->
			SELECT codigo, nome, cidade FROM pessoas
		</query>
		<time>5</time> <!-- intervalo de execucao da query (EM SEGUNDOS) -->
		<api>https://servidor/servico</api> <!-- endereco que serão enviados os valores lidos pela query -->
		<token>123abc</token> <!-- token x-api-token para o endereco(api) -->
	</procedure>
	<procedure>
		<code>2</code>
		<name>VENDAS</name>
		<query>
			SELECT codigo, cliente, valor FROM vendas;
		</query>
		<time>6</time>
		<api>https://webhook</api>
		<token>123abc</token>
	</procedure>
</integration>


Exemplo de arquivo db_config.xml:

<database>
	<dbms>SQL_SERVER</dbms>  <!-- Banco escolhido - valores aceitos: SQL_SERVER, POSTGRESQL, ORACLE -->
	<url>localhost</url>  <!-- Endereco e porta do servidor - ex: localhost:porta, http://192.168.0.0:porta.. -->
	<database>DatabaseName</database> <!-- nome do banco -->
	<user>Isaias</user> <!-- usuario do banco -->
	<password>Santos</password> <!-- senha do banco -->
</database>


