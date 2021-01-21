package io.github.elishaSword.revature.training.project1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.revature.training.project1.models.employee.Employee;
import io.github.elishaSword.revature.training.project1.models.ticket.Ticket;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketStatus;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketStatusDecided;

public class TicketService {
	private final Dao<Integer, Ticket> dao;
	
	public TicketService(Dao<Integer, Ticket> dao) {
		this.dao = dao;
	}

	public Optional<Ticket> getTicketById(int id) {
		return dao.getById(id);
	}
	
	public List<Ticket> getTicketsByEmployee(Employee employee) {
		return dao.getAll().stream()
				.filter(t -> t.getEmployeeUsername().equals(employee.getUsername()))
				.collect(Collectors.toList());
	}

	public List<Ticket> getTicketsByStatus(TicketStatus status) {
		return dao.getAll().stream()
				.filter(t -> t.getStatus().equals(status))
				.collect(Collectors.toList());
	}
	
	public void register(HttpServletRequest request, Ticket ticket) {
		final HttpSession session = request.getSession(false);
		
		if (session != null) {
			dao.insert(ticket.setEmployeeUsername((String) session.getAttribute("username")));
		}
	}
	
	public void decideStatus(Ticket ticket, TicketStatusDecided status) {
		dao.update(ticket.setStatus(status.toTicketStatus()));
	}
}
