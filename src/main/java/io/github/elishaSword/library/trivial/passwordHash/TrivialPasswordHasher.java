package io.github.elishaSword.library.trivial.passwordHash;

import io.github.elishaSword.library.passwordHash.HashedPassword;
import io.github.elishaSword.library.passwordHash.PasswordHasher;

class TrivialPasswordHasher implements PasswordHasher {
	/**
	 * modifies nothing about the unhashedPassword whatsoever
	 */
	@Override
	public HashedPassword hash(String unhashedPassword) {
		return new TriviallyHashedPassword(unhashedPassword);
	}
}
