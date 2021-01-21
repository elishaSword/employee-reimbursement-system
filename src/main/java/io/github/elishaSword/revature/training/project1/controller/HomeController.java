package io.github.elishaSword.revature.training.project1.controller;

import io.github.elishaSword.library.controller.EndpointHandler;

public class HomeController {
	private HomeController() {
		super();
	}
	
	public static final EndpointHandler homeHandler = new EndpointHandler((request, response) -> {
		response.setContentType("application/xhtml+xml");

		if ((boolean) request.getSession(false).getAttribute("isManager")) {
			request.getRequestDispatcher("/ticket-editor/manager.xhtml")
				.forward(request, response);
		} else {
			request.getRequestDispatcher("/ticket-editor/employee.xhtml")
				.forward(request, response);
		}
	}).setRequiresSession("/Project1/api/login");
}
