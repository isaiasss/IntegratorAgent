package com.we3.integrator.dto;

import java.util.ArrayList;
import java.util.HashMap;

public class SendRequestDTO {

	private String code;
	private String name;
	private ArrayList<HashMap<String, Object>> values;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<HashMap<String, Object>> getValues() {
		return values;
	}

	public void setValues(ArrayList<HashMap<String, Object>> values) {
		this.values = values;
	}

}
