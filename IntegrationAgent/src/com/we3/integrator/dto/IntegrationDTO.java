package com.we3.integrator.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "integration")
public class IntegrationDTO {

	private ArrayList<ProcedureDTO> procedure;

	public ArrayList<ProcedureDTO> getProcedure() {
		return procedure;
	}

	public void setProcedure(ArrayList<ProcedureDTO> procedure) {
		this.procedure = procedure;
	}

}
