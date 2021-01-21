package io.github.elishaSword.revature.training.project1.models.employee;

import java.util.Optional;

public enum Privilege {
	EMPLOYEE,
	FINANCE_MANAGER;
	
	public int toInt() {
		int output = -1;

		for (int i = 0; i < Privilege.values().length; ++i) {
			if (this.equals(Privilege.values()[i])) {
				output = i;
			}
		}
		
		return output;
	}

	public static Optional<Privilege> fromInt(int index) {
		final Privilege fromIndex = Privilege.values()[index];
		
		return fromIndex == null
				? Optional.empty()
						: Optional.of(fromIndex);
	}
}
