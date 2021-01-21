package io.github.elishaSword.library.trivial.passwordHash;

import io.github.elishaSword.library.passwordHash.PasswordHasher;

/**
 * the identity hash. just boxes a String up so as to mark it safe for permanent storage.
 * 
 * NOT for use in production.
 */
public class TrivialPasswordHash {
	private final static PasswordHasher trivialPasswordHasher = new TrivialPasswordHasher();

	public static PasswordHasher get() {
		return trivialPasswordHasher;
	}
}
