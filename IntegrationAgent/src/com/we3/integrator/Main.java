package com.we3.integrator;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.we3.integrator.dto.DBConfigDTO;
import com.we3.integrator.dto.IntegrationDTO;
import com.we3.integrator.dto.ProcedureDTO;
import com.we3.integrator.util.DBUtil;
import com.we3.integrator.util.XMLReader;

public class Main {

	private static Logger log = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) {

		log.trace("lendo configuracoes do banco...");
		DBConfigDTO dbConfig = null;

		try {
			dbConfig = XMLReader.readDbConfig();
			log.trace("leitura das configuracoes do banco - OK");
		} catch (FileNotFoundException e) {
			log.error("arquivo db_config.xml nao encontrado");
			return;
		} catch (JAXBException e) {
			log.error("formato invalido do arquivo db_config.xml");
			return;
		}

		log.trace("lendo processos...");
		IntegrationDTO integration;
		try {
			integration = XMLReader.readProcedures();
			log.trace("leitura dos processos - OK");
		} catch (FileNotFoundException e) {
			log.error("arquivo integration.xml nao encontrado");
			return;
		} catch (JAXBException e) {
			log.error("formato invalido do arquivo integration.xml");
			return;
		}

		log.trace("validando a conexao com banco...");
		try {
			Connection conn = DBUtil.getConnection(dbConfig);
			conn.close();
			log.trace("validacao de conexao com banco - OK");
		} catch (ClassNotFoundException e) {
			log.error("arquivo de driver JDBC nao encontrado");
			return;
		} catch (SQLException e) {
			log.error("erro ao criar conexao com banco", e);
			return;
		}

		for (final ProcedureDTO proc : integration.getProcedure()) {
			long seconds = new Long(proc.getTime()) * 1000;

			log.trace("iniciando objeto processo de código " + proc.getCode());
			final ExecProcedure exec = new ExecProcedure(proc, dbConfig);
			log.trace("tempo em segundos: " + proc.getTime());

			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					{
						log.trace("executando processo de código " + proc.getCode());
						try {
							exec.execute();
							log.trace("fim processo de código " + proc.getCode() + " - OK");
						} catch (Exception e) {
							log.error("fim processo de código " + proc.getCode() + " com erro", e);
						}

					}
				}
			}, 0, seconds);
		}
	}

}
