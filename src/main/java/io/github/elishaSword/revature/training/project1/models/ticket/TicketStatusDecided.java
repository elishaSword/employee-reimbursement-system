package io.github.elishaSword.revature.training.project1.models.ticket;

import java.util.Objects;
import java.util.Optional;

public enum TicketStatusDecided {
	ACCEPTED,
	REJECTED;
	
	public TicketStatus toTicketStatus() {
		final TicketStatus output;
		
		if (this.equals(ACCEPTED)) {
			output = TicketStatus.ACCEPTED;
		} else {
			output = TicketStatus.REJECTED;
		}
		
		return output;
	}
	
	public static Optional<TicketStatusDecided> fromTicketStatus(TicketStatus status) {
		final Optional<TicketStatusDecided> output;
		
		if (Objects.equals(status, TicketStatus.ACCEPTED)) {
			output = Optional.of(ACCEPTED);
		} else if (Objects.equals(status, TicketStatus.REJECTED)) {
			output = Optional.of(REJECTED);
		} else {
			output = Optional.empty();
		}
		
		return output;
	}
}
