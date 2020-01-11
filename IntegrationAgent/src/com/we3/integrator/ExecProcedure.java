package com.we3.integrator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.we3.integrator.dto.DBConfigDTO;
import com.we3.integrator.dto.ProcedureDTO;
import com.we3.integrator.dto.SendRequestDTO;
import com.we3.integrator.util.DBUtil;

public class ExecProcedure {

	private static Logger log = LogManager.getLogger(ExecProcedure.class.getName());

	private ProcedureDTO proc;
	private DBConfigDTO dbConfig;

	public ExecProcedure(ProcedureDTO proc, DBConfigDTO dbConfig) {
		this.proc = proc;
		this.dbConfig = dbConfig;
	}

	public void execute() throws SQLException, Exception {

		Connection con = DBUtil.getConnection(dbConfig);
		Statement stmt = null;
		ResultSet rs = null;

		ArrayList<HashMap<String, Object>> values;

		try {
			String sql = proc.getQuery();

			stmt = con.createStatement();

			log.trace("executando query");
			rs = stmt.executeQuery(sql);

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			values = new ArrayList<HashMap<String, Object>>();
			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<String, Object>(columns);
				for (int i = 1; i <= columns; ++i) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				values.add(row);
			}

			log.trace("quantidade de registros retornados: " + values.size());

		} catch (Exception e) {
			log.error("erro na execucao da query. processo de codigo " + proc.getCode(), e);
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
			}
		}

		log.trace("enviando para api");
		sendValuesToServer(values);
	}

	private void sendValuesToServer(ArrayList<HashMap<String, Object>> values) throws Exception {
		SendRequestDTO request = new SendRequestDTO();
		request.setValues(values);
		request.setCode(proc.getCode());
		request.setName(proc.getName());

		String content = new Gson().toJson(request);

		HttpURLConnection connection = null;
		OutputStream os = null;

		try {
			URL postUrl = new URL(proc.getApi());
			connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("x-api-token", proc.getToken());

			os = connection.getOutputStream();
			os.write(content.getBytes("UTF-8"));
			os.flush();

			InputStream is;

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			StringBuilder sbResp = new StringBuilder();

			String line = reader.readLine();
			while (line != null) {
				sbResp.append(line);
				line = reader.readLine();
			}

			log.trace("retorno api: " + sbResp.toString());
		} catch (Exception e) {
			log.error("erro envio api: ", e);
			throw e;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
				}
			}

			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception e) {
				}
			}
		}
	}

}
