package io.github.elishaSword.library.servlet;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface ServletHandler {
	public void accept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	public default ServletHandler andThen(ServletHandler after) {
		Objects.requireNonNull(after);
		
		return (request, response) -> {
			this.accept(request, response);
			after.accept(request, response);
		};
	}
	
	public default ServletHandler redirectIfSessionless(String uri) {
		Objects.requireNonNull(uri);

		return (request, response) -> {
			if (request.getSession(false) == null) {
				response.sendRedirect(uri);
			} else {
				this.accept(request, response);
			}
		};
	}
}
