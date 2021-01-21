package io.github.elishaSword.revature.training.project1.test.experimentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringSplitTest {
	@Test
	void test() {
		final String[] tokens = "/Project1/api/ticket/42069/"
				.split("/");
		
		assertTrue(tokens.length >= 4);
		assertEquals(tokens[4], "42069");
	}
}
