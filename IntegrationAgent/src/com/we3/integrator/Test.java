package com.we3.integrator;

import java.sql.Connection;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.we3.integrator.dto.DBConfigDTO;
import com.we3.integrator.dto.IntegrationDTO;
import com.we3.integrator.dto.ProcedureDTO;
import com.we3.integrator.util.DBUtil;
import com.we3.integrator.util.XMLReader;

public class Test {

	private static Logger log = LogManager.getLogger(Test.class.getName());

	public static void main(String[] args) {

		try {
			DBConfigDTO dbConfig = XMLReader.readDbConfig();
			log.trace("leitura das configuracoes do banco - OK");

			IntegrationDTO integration = XMLReader.readProcedures();
			log.trace("leitura dos processos - OK");

			Connection conn = DBUtil.getConnection(dbConfig);
			conn.close();
			log.trace("validacao de conexao com banco - OK");

			for (final ProcedureDTO proc : integration.getProcedure()) {

				final ExecProcedure exec = new ExecProcedure(proc, dbConfig);

				final Timer t = new Timer();
				t.schedule(new TimerTask() {
					@Override
					public void run() {

						try {
							exec.execute();
							log.trace("fim processo de código " + proc.getCode() + " - OK");
						} catch (Exception e) {
							log.error("fim processo de código " + proc.getCode() + " com erro", e);
						}

						t.cancel();
					}
				}, 0, 60000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erro", e);
		}
	}

}
