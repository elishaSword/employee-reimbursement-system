package io.github.elishaSword.library.parser;

import java.util.Optional;

public class SafeIntParser {
	private SafeIntParser() {
		super();
	}
	
	public static Optional<Integer> parse(String s) {
		Optional<Integer> output;
		
		try {
			output = Optional.of(Integer.parseInt(s));
		} catch (NumberFormatException e) {
			output = Optional.empty();
		}
		
		return output;
	}
}
