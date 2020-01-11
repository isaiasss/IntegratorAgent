package com.we3.integrator.util;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.we3.integrator.dto.DBConfigDTO;
import com.we3.integrator.dto.IntegrationDTO;

public class XMLReader {

	public static DBConfigDTO readDbConfig() throws JAXBException, FileNotFoundException {

		File file = new File("db_config.xml");
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		JAXBContext jaxbContext = JAXBContext.newInstance(DBConfigDTO.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		DBConfigDTO customer = (DBConfigDTO) jaxbUnmarshaller.unmarshal(file);

		return customer;
	}

	public static IntegrationDTO readProcedures() throws JAXBException, FileNotFoundException {

		File file = new File("integration.xml");
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		JAXBContext jaxbContext = JAXBContext.newInstance(IntegrationDTO.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		IntegrationDTO customer = (IntegrationDTO) jaxbUnmarshaller.unmarshal(file);
		return customer;
	}
}
