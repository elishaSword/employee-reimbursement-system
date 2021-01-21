package io.github.elishaSword.revature.training.project1.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.elishaSword.library.controller.EndpointHandler;
import io.github.elishaSword.library.parser.SafeIntParser;
import io.github.elishaSword.library.servlet.ServletHandler;
import io.github.elishaSword.revature.training.project1.models.ticket.Ticket;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketStatus;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketStatusDecided;
import io.github.elishaSword.revature.training.project1.service.EmployeeService;
import io.github.elishaSword.revature.training.project1.service.TicketService;

public class TicketController {
	private TicketService service;
	private EmployeeService employeeService;
	private static final Logger logger = LogManager.getLogger();

	public TicketController(TicketService service, EmployeeService employeeService) {
		this.service = service;
		this.employeeService = employeeService;
	}
	
	private final ServletHandler getHandlerIdInUri = (request, response) -> {
		doGet(response,
				getIdFromUri(request)
					.flatMap(service::getTicketById));

	};
	
	private final ServletHandler getHandlerGeneric = (request, response) -> {
		doGet(response,
				Optional.ofNullable(request.getParameter("id"))
					.flatMap(SafeIntParser::parse)
					.flatMap(service::getTicketById));
	};
	
	private final ServletHandler putHandlerIdInUri = (request, response) -> {
		final Optional<Ticket> t = getIdFromUri(request)
				.flatMap(service::getTicketById);
		
		if (!t.isPresent()) {
			response.sendError(404);
		} else {
			Ticket input;
			Exception exception;
			
			try {
				input = new ObjectMapper()
						.readValue(request.getReader(), Ticket.class);
				exception = null;
			} catch (JsonParseException | JsonMappingException e) {
				exception = e;
				input = null;
			}
			
			if (exception != null) {
				response.setStatus(400);
				logger.error(exception);

				new ObjectMapper()
					.writeValue(response.getWriter(), exception);
			} else if (t.get().getId() != input.getId()) {
				response.sendError(404);
			} else {
				doPut(response, t.get(), input.getStatus());
			}
		}
	};
	
	private final ServletHandler postHandler = (request, response) -> {
		Ticket input;
		Exception exception;

		try {
			input = new ObjectMapper()
					.readValue(request.getReader(), Ticket.class);
			exception = null;
		} catch (JsonParseException | JsonMappingException e) {
			exception = e;
			logger.info(e);
			input = null;
		}
		
		if (exception != null) {
			response.setStatus(400);
			new ObjectMapper()
				.writeValue(response.getWriter(), exception);
		} else {
			service.register(request, input);
		}
	};
	
	private final ServletHandler putHandlerGeneric = (request, response) -> {
		Ticket input;
		Exception exception;
		
		try {
			input = new ObjectMapper()
					.readValue(request.getReader(), Ticket.class);
			exception = null;
		} catch (JsonParseException | JsonMappingException e) {
			exception = e;
			logger.info(e);
			input = null;
		}
		
		if (exception != null) {
			response.setStatus(400);
			new ObjectMapper()
				.writeValue(response.getWriter(), exception);
		} else {
			Optional<Ticket> t = service.getTicketById(input.getId());

			if (!t.isPresent()) {
				response.sendError(404);
			} else {
				doPut(response, t.get(), t.get().getStatus());
			}
		}
	};
	
	public final EndpointHandler ticketHandler = new EndpointHandler((request, response) -> {
		if (idIsInUri(request)) {
			getHandlerIdInUri.accept(request, response);
		} else {
			getHandlerGeneric.accept(request, response);
		}
	}).allowPost((request, response) -> {
		if (idIsInUri(request)) {
			response.setStatus(405);
			response.sendRedirect("/Project1/api/");
		} else {
			postHandler.accept(request, response);
		}
	}).allowPut((request, response) -> {
		if (idIsInUri(request)) {
			putHandlerIdInUri.accept(request, response);
		} else {
			putHandlerGeneric.accept(request, response);
		}
	}).setRequiresSession("/Project1/api/login");
	
	public final EndpointHandler ticketHandlerByStatus = new EndpointHandler((request, response) -> {
		final Optional<TicketStatus> status = getStatusFromUri(request);
		
		if (!status.isPresent()) {
			response.sendError(500);
		} else {
			new ObjectMapper()
				.writeValue(response.getWriter(), service.getTicketsByStatus(status.get()));
		}
	}).setRequiresSession("/Project1/api/login");
	
	public final EndpointHandler ticketHandlerByEmployee = new EndpointHandler((request, response) ->
		new ObjectMapper()
			.writeValue(
				response.getWriter(),
				service.getTicketsByEmployee(
						employeeService.getEmployeeByUsername((String) request.getSession(false).getAttribute("username"))
							.get()))
	).setRequiresSession("/Project1/api/login");
	
	private static void doGet(HttpServletResponse response, Optional<Ticket> t) throws IOException {
		if (!t.isPresent()) {
			response.sendError(404);
		} else {
			try {
				new ObjectMapper()
					.writeValue(response.getWriter(), t.get());
			} catch (JsonGenerationException | JsonMappingException e) {
				try {
					response.setStatus(500);
					logger.error(e);
					new ObjectMapper()
						.writeValue(response.getWriter(), e);
				} catch (Exception e2) {
					logger.error(e);
				}
			}
		}
	}
	
	private void doPut(HttpServletResponse response, Ticket t, TicketStatus decision) throws IOException {
		Optional<TicketStatusDecided> status = TicketStatusDecided.fromTicketStatus(decision);
		
		if (status.isPresent()) {
			service.decideStatus(t, status.get());
			
			try {
				new ObjectMapper()
					.writeValue(response.getWriter(), t);
			} catch (JsonGenerationException | JsonMappingException e) {
				try {
					response.setStatus(500);
					logger.error(e);
					new ObjectMapper()
						.writeValue(response.getWriter(), e);
				} catch (Exception e2) {
					logger.error(e);
				}
			}
		} else {
			response.sendError(400);
		}
	}
	
	private static Optional<Integer> getIdFromUri(HttpServletRequest request) {
		final String[] tokens = request.getRequestURI()
				.split("/");
		
		return tokens.length >= 5
				? SafeIntParser.parse(tokens[4])
						: Optional.empty();
	}
	
	private static boolean idIsInUri(HttpServletRequest request) {
		return getIdFromUri(request)
				.isPresent();
	}
	
	private static Optional<TicketStatus> getStatusFromUri(HttpServletRequest request) {
		final String[] tokens = request.getRequestURI()
				.split("/");
		
		return tokens.length >= 5
				? TicketStatus.fromString(tokens[4])
						: Optional.empty();
	}
}
