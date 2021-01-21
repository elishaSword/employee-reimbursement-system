package io.github.elishaSword.revature.training.project1.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.elishaSword.library.controller.EndpointHandler;

public class EmployeeController {
	private static final Logger logger = LogManager.getLogger();

	private EmployeeController() {
		super();
	}
	
	public static final EndpointHandler getThisEmployeeHandler = new EndpointHandler((request, response) -> {
		try {
			new ObjectMapper()
				.writeValue(response.getWriter(), request.getSession(false).getAttribute("username"));
		} catch (JsonGenerationException | JsonMappingException e) {
			try {
				response.setStatus(500);
				new ObjectMapper()
					.writeValue(response.getWriter(), e);
			} catch (Exception e2) {
				logger.error(e2);
			}
		}
	}).setRequiresSession("/Project1/api/login");
}
