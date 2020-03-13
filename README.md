# IntegratorAgent

IntegratorAgent é um projeto escrito em Java com objetivo de ler tabelas de banco de dados e enviar para um Webservice.

Todas as consultas SQL e paramêtros de conexão com o banco são configuravéis, assim como o Webservice que será chamado após cada consulta.

## Instalação

1-Realizar checkout deste projeto

2-Configurar os arquivos de configuração (db_config.xml e integration.xml)

3-Rodar a classe Main (com.we3.integrador.Main)

## Processo

-Cada query executada, os valores lidos serão enviados para a API configurada

-Logs serão gerados em cada execução ficando no arquivo app.log

## Dependências Maven

-Gson

-Apache Log4J

-Driver JDBC

-Java JRE JDK 8

## Configuração

Exemplo integration.xml:

```xml
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
```

Exemplo db_config.xml:

```xml
<database>
	<dbms>SQL_SERVER</dbms>  <!-- Banco escolhido - valores aceitos: SQL_SERVER, POSTGRESQL, ORACLE -->
	<url>localhost</url>  <!-- Endereco e porta do servidor - ex: localhost:porta, http://192.168.0.0:porta.. -->
	<database>DatabaseName</database> <!-- nome do banco -->
	<user>Isaias</user> <!-- usuario do banco -->
	<password>Santos</password> <!-- senha do banco -->
</database>
```
