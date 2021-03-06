package com.we3.integrator.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "database")
public class DBConfigDTO {

	private String dbms;
	private String url;
	private String database;
	private String user;
	private String password;
	private String custom_url;
	private String custom_class;

	public String getDbms() {
		return dbms;
	}

	public void setDbms(String dbms) {
		this.dbms = dbms;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCustom_url() {
		return custom_url;
	}

	public void setCustom_url(String custom_url) {
		this.custom_url = custom_url;
	}

	public String getCustom_class() {
		return custom_class;
	}

	public void setCustom_class(String custom_class) {
		this.custom_class = custom_class;
	}

}
