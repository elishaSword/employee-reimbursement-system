package io.github.elishaSword.revature.training.project1.models.ticket;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TicketType {
	FOOD,
	LODGING,
	TRAVEL,
	OTHER;
	
	public int toInt() {
		int output = -1;

		for (int i = 0; i < TicketType.values().length; ++i) {
			if (this.equals(TicketType.values()[i])) {
				output = i;
			}
		}
		
		return output;
	}
	
	public static Optional<TicketType> fromInt(int index) {
		final TicketType fromIndex = TicketType.values()[index];
		
		return fromIndex == null
				? Optional.empty()
						: Optional.of(fromIndex);
	}
	
	
	@JsonCreator
	public static TicketType fromString(String string) {
		TicketType output = null;
		
		for (TicketType t : TicketType.values()) {
			if (t.toString().equalsIgnoreCase(string)) {
				output = t;
				break;
			}
		}
		
		return output;
	}
}
