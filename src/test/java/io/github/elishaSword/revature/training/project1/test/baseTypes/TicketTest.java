package io.github.elishaSword.revature.training.project1.test.baseTypes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.elishaSword.revature.training.project1.models.ticket.TicketType;

class TicketTest {
	@Test
	void toInt() {
		assertEquals(TicketType.FOOD.toInt(), 0);
		assertEquals(TicketType.LODGING.toInt(), 1);
		assertEquals(TicketType.TRAVEL.toInt(), 2);
		assertEquals(TicketType.OTHER.toInt(), 3);
	}
}
