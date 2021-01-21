package io.github.elishaSword.revature.training.project1.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.elishaSword.library.controller.Router;
import io.github.elishaSword.revature.training.project1.controller.AuthenticationController;
import io.github.elishaSword.revature.training.project1.controller.EmployeeController;
import io.github.elishaSword.revature.training.project1.controller.HomeController;
import io.github.elishaSword.revature.training.project1.controller.TicketController;
import io.github.elishaSword.revature.training.project1.dao.EmployeeDao;
import io.github.elishaSword.revature.training.project1.dao.TicketDao;
import io.github.elishaSword.revature.training.project1.service.EmployeeService;
import io.github.elishaSword.revature.training.project1.service.TicketService;

@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = -8554129645108518902L;	
	
	private final Router router = new Router("application/json");
	
	private final EmployeeService employeeService = new EmployeeService(EmployeeDao.get());
	private final TicketService ticketService = new TicketService(TicketDao.get());
	
	private final AuthenticationController authenticationController = new AuthenticationController(employeeService);
	private final TicketController ticketController = new TicketController(ticketService, employeeService);
	
	public ApiServlet() {
		router.put("/Project1/api(/?|/home/?)", HomeController.homeHandler);
		
		router.put("/Project1/api/login/?", authenticationController.loginHandler);
		router.put("/Project1/api/logout/?", authenticationController.logoutHandler);
		router.put("/Project1/api/register/?", authenticationController.registrationHandler);
		
		router.put("/Project1/api/ticket(/?|/[0-9]+/?)", ticketController.ticketHandler);
		router.put("/Project1/api/ticket/(accepted|rejected|todo)/?", ticketController.ticketHandlerByStatus);
		router.put("/Project1/api/ticket/employee/?", ticketController.ticketHandlerByEmployee);
		
		router.put("/Project1/api/employee/?", EmployeeController.getThisEmployeeHandler);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		router.handle(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		router.handle(request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		router.handle(request, response);
	}
}
