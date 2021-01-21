package io.github.elishaSword.revature.training.project1.controller;

import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.elishaSword.library.controller.EndpointHandler;
import io.github.elishaSword.library.passwordHash.PasswordHasher;
import io.github.elishaSword.library.trivial.passwordHash.TrivialPasswordHash;
import io.github.elishaSword.revature.training.project1.models.employee.Employee;
import io.github.elishaSword.revature.training.project1.models.employee.Privilege;
import io.github.elishaSword.revature.training.project1.service.EmployeeService;

public class AuthenticationController {
	private EmployeeService employeeService;
	private static final PasswordHasher hasher = TrivialPasswordHash.get();
	private static final Logger logger = LogManager.getLogger();
	
	public AuthenticationController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public final EndpointHandler loginHandler = new EndpointHandler((request, response) -> {
		response.setContentType("application/xhtml+xml");
		final RequestDispatcher requestDispatcher;
		
		if (request.getParameter("registration") != null) {
			requestDispatcher = request.getRequestDispatcher("/login-registration.xhtml");
		} else {
			requestDispatcher = request.getRequestDispatcher("/login.xhtml");
		}
		
		requestDispatcher.forward(request, response);
	}).allowPost((request, response) -> {
		final String username = request.getParameter("username");
		final String unhashedPassword = request.getParameter("unhashedPassword");
		
		response.setContentType("application/xhtml+xml");
		
		Optional<Employee> employee = employeeService.verifyCredentials(username, hasher.hash(unhashedPassword));
		if (username == null || unhashedPassword == null) {
			response.sendError(400);
		} else if (!employee.isPresent()) {
			logger.info("bad login for: {}", username);
			request.getRequestDispatcher("/login-fail.xhtml")
				.forward(request, response);
		} else {
			logger.info("logged in: {}", username);
			HttpSession session = request.getSession(true);
			
			session.setAttribute("username", employee.get()
					.getUsername());
			session.setAttribute("isManager", employee.get()
					.getPrivilege().equals(Privilege.FINANCE_MANAGER));

			response.sendRedirect("/Project1/api/");
		}
	});
	
	public final EndpointHandler registrationHandler = new EndpointHandler((request, response) -> {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		response.setContentType("application/xhtml+xml");
		request.getRequestDispatcher("/register.xhtml")
			.forward(request, response);
	}).allowPost((request, response) -> {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		final String username = request.getParameter("username");
		final String unhashedPassword = request.getParameter("unhashedPassword");
		
		response.setContentType("application/xhtml+xml");
		if (username == null || unhashedPassword == null) {
			response.sendError(400);
		} else {
			employeeService.register(new Employee(username, hasher.hash(unhashedPassword), Privilege.EMPLOYEE));
			logger.info("registered: {}", username);

			response.sendRedirect("/Project1/api/login?registration=true");
		}
	});
	
	public final EndpointHandler registrationSuccessHandler = new EndpointHandler((request, response) -> {
		request.getRequestDispatcher("/login-registration.xhtml")
			.forward(request, response);
	});
	
	public final EndpointHandler logoutHandler = new EndpointHandler((request, response) -> {
		logger.info("logged out: {}", request.getSession(false).getAttribute("username"));
		request.getSession(false).invalidate();

		response.sendRedirect("/Project1/api/");
	}).setRequiresSession("/Project1/api/login");
}
