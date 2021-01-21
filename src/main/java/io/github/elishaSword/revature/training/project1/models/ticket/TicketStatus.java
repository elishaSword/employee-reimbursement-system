package io.github.elishaSword.revature.training.project1.models.ticket;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TicketStatus {
	TODO,
	ACCEPTED,
	REJECTED;
	
	public int toInt() {
		int output = -1;
		
		for (int i = 0; i < TicketStatus.values().length; ++i) {
			if (this.equals(TicketStatus.values()[i])) {
				output = i;
			}
		}
		
		return output;
	}
	
	public static Optional<TicketStatus> fromInt(int index) {
		return index >= TicketStatus.values().length
				? Optional.empty()
						: Optional.of(TicketStatus.values()[index]);
	}
	
	public static Optional<TicketStatus> fromString(String string) {
		Optional<TicketStatus> output = Optional.empty();
		
		for (TicketStatus s : TicketStatus.values()) {
			if (s.toString().equalsIgnoreCase(string)) {
				output = Optional.of(s);
				break;
			}
		}
		
		return output;
	}
	
	@JsonCreator
	public static TicketStatus ofString(String string) {
		TicketStatus output = null;
		
		for (TicketStatus s : TicketStatus.values()) {
			if (s.toString().equalsIgnoreCase(string)) {
				output = s;
				break;
			}
		}
		
		return output;
	}
}
