package io.github.elishaSword.revature.training.project1.test.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.revature.training.project1.dao.TicketDao;
import io.github.elishaSword.revature.training.project1.models.ticket.Ticket;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketStatus;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketType;

class TicketDaoTest {
	private static final Dao<Integer, Ticket> dao = TicketDao.get();
	private static final List<Ticket> initialTickets = dao.getAll();
	
	@Test
	void insertTest() {
		final Ticket t0 = new Ticket(42069, "alex", TicketType.TRAVEL, "a mediocre plane", TicketStatus.TODO, new Date(42069), 20000);
		final Ticket t1 = new Ticket(42069, "alex", TicketType.LODGING, "a nice hotel", TicketStatus.TODO, new Date(42070), 70000);
		final Ticket t2 = new Ticket(42069, "alex", TicketType.FOOD, "a bad restaurant", TicketStatus.TODO, new Date(42071), 5000);
		
		final int oldSize = dao.getAll().size();
		
		insert(t0);
		insert(t1);
		insert(t2);
		
		final List<Ticket> afterInsert = dao.getAll();

		assertEquals(afterInsert.size(), oldSize + 3);
		assertTrue(softlyContains(afterInsert, t0));
		assertTrue(softlyContains(afterInsert, t1));
		assertTrue(softlyContains(afterInsert, t2));
	}
	
	@Test
	void getTest() {
		final Ticket t0 = new Ticket(-1, "alex", TicketType.TRAVEL, "a trash uber", TicketStatus.TODO, new Date(42069), 1000);
		final Ticket t1 = new Ticket(-1, "alex", TicketType.LODGING, "a great hotel", TicketStatus.TODO, new Date(42070), 100000);
		final Ticket t2 = new Ticket(-1, "alex", TicketType.FOOD, "a fantastic restaurant", TicketStatus.TODO, new Date(42071), 6000);
		
		final int oldSize = dao.getAll().size();
		
		insert(t0);
		insert(t1);
		insert(t2);
		
		final List<Ticket> afterInsert = dao.getAll();
		
		assertEquals(afterInsert.size(), oldSize + 3);
		assertTrue(softlyContains(afterInsert, t0));
		assertTrue(softlyContains(afterInsert, t1));
		assertTrue(softlyContains(afterInsert, t2));
	}
	
	@Test
	void updateTest() {
		final Ticket t0 = new Ticket(42069, "alex", TicketType.TRAVEL, "a bad hotel", TicketStatus.TODO, new Date(42072), 20000);
		insert(t0);
		
		final Ticket t0Approved = dao.getAll().stream()
			.filter(t -> t.getEmployeeUsername().equals(t0.getEmployeeUsername())
					&& t.getDescription().equals(t0.getDescription())
					&& t.getPrice() == t0.getPrice())
			.findAny().get()
			.setStatus(TicketStatus.ACCEPTED);
		update(t0Approved);
		
		// we only update ticket statuses!
		dao.update(t0Approved.setPrice(30));
		assertFalse(softlyContains(dao.getAll(), t0Approved));
		
		update(t0);
	}

	@AfterAll
	public static void resetAndInDoingSoTestDelete() {
		System.out.println("clearing");

		for (Ticket t : dao.getAll()) {
			if (!initialTickets.contains(t)) {
				delete(t);
			}
		}

		final List<Ticket> hopefullInitialTickets = dao.getAll();

		assertEquals(initialTickets.size(), hopefullInitialTickets.size());
		for (Ticket t : hopefullInitialTickets) {
			assertTrue(initialTickets.contains(t));
		}
	}
	
	private static void insert(Ticket t) {
		dao.insert(t);
		
		assertTrue(softlyContains(dao.getAll(), t));
	}
	
	private static void update(Ticket replacement) {
		if (dao.getById(replacement.getId()).isPresent()) {
			dao.update(replacement);
			assertTrue(softlyContains(dao.getAll(), replacement));
		}
	}
	
	private static void delete(Ticket t) {
		dao.delete(t);
		
		assertFalse(softlyContains(dao.getAll(), t));
	}
	
	/**
	 * sometimes you create a ticket with a mock id; this lets you see if the database contains an equivalent
	 * @param list a List of Tickets
	 * @param t a Ticket, likely containing an invalid or mock id
	 * @return whether an equivalent ticket is in the provided list
	 */
	private static boolean softlyContains(List<Ticket> list, Ticket t) {
		return list.stream()
				.filter(candidate -> candidate.getDescription().equals(t.getDescription())
						&& candidate.getEmployeeUsername().equals(t.getEmployeeUsername())
						&& candidate.getPrice() == t.getPrice())
				.findFirst()
				.isPresent();
	}
}
