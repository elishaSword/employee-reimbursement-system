package io.github.elishaSword.revature.training.project1.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.library.passwordHash.PasswordHasher;
import io.github.elishaSword.library.trivial.passwordHash.TrivialPasswordHash;
import io.github.elishaSword.revature.training.project1.models.employee.Employee;
import io.github.elishaSword.revature.training.project1.models.employee.Privilege;
import io.github.elishaSword.revature.training.project1.models.ticket.Ticket;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketStatus;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketStatusDecided;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketType;
import io.github.elishaSword.revature.training.project1.service.TicketService;

class TicketServiceTest {
	private static final Dao<Integer, Ticket> dao = TicketDaoMockFactory.get();
	private static final TicketService service = new TicketService(dao);
	private static final PasswordHasher hasher = TrivialPasswordHash.get();
	private static final Employee[] users = {new Employee("alex", hasher.hash(""), Privilege.EMPLOYEE), new Employee("deputy", hasher.hash(""), Privilege.FINANCE_MANAGER)};
	
	@BeforeAll
	public static void setup() {
		int i = 42069;
		int j = 420;
		int k = 6900;
		
		String[] assessments = {"trash", "excellent"};
		
		for (Employee e : users) {
			for (String a : assessments) {
				for (TicketType tt : TicketType.values()) {
					for (TicketStatus s : TicketStatus.values()) {
						dao.insert(new Ticket(i++, e.getUsername(), tt, "some " + a + " " + tt.toString().toLowerCase(), s, new Date(j++), k++));
					}
				}
			}
		}
	}
	
	@Test
	void register() {
		final Ticket t = new Ticket(420, "alex", TicketType.FOOD, "a unique restaurant", TicketStatus.TODO, new Date(42300), 10000);
		final int initialSize = dao.getAll().size();
				
		dao.insert(t);
		
		assertEquals(initialSize + 1, dao.getAll().size());
	}
	
	@Test
	void get() {
		for (Ticket t0 : dao.getAll()) {
			assertEquals(Optional.of(t0), dao.getAll().stream()
					.filter(t -> t.getId() == t0.getId())
					.findFirst());
		}
	}
	
	@Test
	void filters() {
		for (Employee e : users) {
			for (Ticket t : service.getTicketsByEmployee(e)) {
				assertEquals(e.getUsername(), t.getEmployeeUsername());
			}
		}
		
		for (TicketStatus s : TicketStatus.values()) {
			for (Ticket t : service.getTicketsByStatus(s)) {
				assertEquals(s, t.getStatus());
			}
		}
	}
	
	@Test
	void decide() {
		List<Ticket> oldTodos = service.getTicketsByStatus(TicketStatus.TODO);
		List<Ticket> oldRejecteds = service.getTicketsByStatus(TicketStatus.REJECTED);

		final int oldTodosSize = oldTodos.size();
		final int oldRejectedsSize = oldRejecteds.size();

		int numberRejected = 0;		
		for (Ticket t : service.getTicketsByStatus(TicketStatus.TODO)) {
			service.decideStatus(t, TicketStatusDecided.REJECTED);
			++numberRejected;
		}
		
		assertEquals(oldTodosSize - numberRejected, service.getTicketsByStatus(TicketStatus.TODO).size());
		assertEquals(oldRejectedsSize + numberRejected, service.getTicketsByStatus(TicketStatus.REJECTED).size());
	}
}
