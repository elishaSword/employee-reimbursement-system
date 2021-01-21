package io.github.elishaSword.revature.training.project1.test.baseTypes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import io.github.elishaSword.library.passwordHash.PasswordHasher;
import io.github.elishaSword.library.trivial.passwordHash.TrivialPasswordHash;

class TriviallyHashedPasswordTest {
	private static final PasswordHasher hasher = TrivialPasswordHash.get();

	@Test
	void testEquals() {
		assertEquals(hasher.hash(new String("a")), hasher.hash(new String("a")));
		assertNotEquals(hasher.hash(new String("b")), hasher.hash(new String("a")));
		assertNotEquals(hasher.hash(new String("a")), hasher.hash(new String("b")));
		assertEquals(hasher.hash(new String("b")), hasher.hash(new String("b")));
	}
}
