package com.we3.integrator.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.we3.integrator.dto.DBConfigDTO;

public class DBUtil {

	private static final String SQL_SERVER = "SQL_SERVER";
	private static final String POSTGRESQL = "POSTGRESQL";
	private static final String ORACLE = "ORACLE";
	private static final String CUSTOM = "CUSTOM";

	public static Connection getConnection(DBConfigDTO dbConfig) throws ClassNotFoundException, SQLException {
		switch (dbConfig.getDbms()) {
		case SQL_SERVER:
			return getSQLServerConn(dbConfig);
		case POSTGRESQL:
			return getPostgreSQLConn(dbConfig);
		case ORACLE:
			return getOracleConn(dbConfig);
		case CUSTOM:
			return getCustomConn(dbConfig);
		default:
			return getPostgreSQLConn(dbConfig);
		}
	}

	private static Connection getSQLServerConn(DBConfigDTO dbConfig) throws ClassNotFoundException, SQLException {
		String url = String.format("jdbc:sqlserver://%s;databaseName=%s;user=%s;password=%s;", dbConfig.getUrl(),
				dbConfig.getDatabase(), dbConfig.getUser(), dbConfig.getPassword());
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		Connection con = DriverManager.getConnection(url);
		return con;
	}

	private static Connection getPostgreSQLConn(DBConfigDTO dbConfig) throws ClassNotFoundException, SQLException {
		String url = String.format("jdbc:postgresql://%s/%s", dbConfig.getUrl(), dbConfig.getDatabase());
		Class.forName("org.postgresql.Driver");

		Connection conn = DriverManager.getConnection(url, dbConfig.getUser(), dbConfig.getPassword());
		return conn;
	}

	private static Connection getOracleConn(DBConfigDTO dbConfig) throws ClassNotFoundException, SQLException {
		String url = String.format("jdbc:oracle:thin:@%s:%s", dbConfig.getUrl(), dbConfig.getDatabase());
		Class.forName("oracle.jdbc.driver.OracleDriver");

		Connection conn = DriverManager.getConnection(url, dbConfig.getUser(), dbConfig.getPassword());
		return conn;
	}

	private static Connection getCustomConn(DBConfigDTO dbConfig) throws ClassNotFoundException, SQLException {
		String url = dbConfig.getCustom_url();
		Class.forName(dbConfig.getCustom_class());

		Connection con = DriverManager.getConnection(url);
		return con;
	}

}
