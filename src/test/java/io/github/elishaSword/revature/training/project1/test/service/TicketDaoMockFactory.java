package io.github.elishaSword.revature.training.project1.test.service;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.stubbing.Answer;

import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.revature.training.project1.dao.TicketDao;
import io.github.elishaSword.revature.training.project1.models.ticket.Ticket;

public class TicketDaoMockFactory {
	private static final Dao<Integer, Ticket> dao = mock(TicketDao.class);
	private static final List<Ticket> allTickets = new ArrayList<>();

	private TicketDaoMockFactory() {
		super();
	}
	
	public static Dao<Integer, Ticket> get() {
		when(dao.getAll())
			.thenReturn(allTickets);
	
		doAnswer((Answer<Optional<Ticket>>) (invocation) -> {
			return allTickets.stream()
					.filter(t -> t.equals(invocation.getArgument(0)))
					.findFirst();
		}).when(dao).getById(anyInt());
		
		doAnswer((Answer<Ticket>) (invocation) -> {
			allTickets.add(invocation.getArgument(0));
			return null;
		}).when(dao).insert(isA(Ticket.class));
		
		doAnswer((Answer<Ticket>) (invocation) -> {
			Ticket t = invocation.getArgument(0);

			allTickets.removeIf(candidate -> candidate.getId() == t.getId());
			allTickets.add(t);
			return null;
		}).when(dao).update(isA(Ticket.class));
		
		return dao;
	}
}
