package io.github.elishaSword.library.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Router {
	private final Map<String, EndpointHandler> router = new LinkedHashMap<>();
	private final String contentType;
	private static final Logger logger = LogManager.getLogger();
	
	public Router(String contentType) {
		Objects.requireNonNull(contentType);
		
		this.contentType = contentType;
	}

	public EndpointHandler put(String pattern, EndpointHandler handler) {
		return router.put(pattern, handler);
	}

	public boolean supports(HttpServletRequest request) {
		return findMatch(request)
			.map(pattern -> true).orElse(false);
	}
	
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		final Optional<String> matchingPattern = findMatch(request);
		
		response.setContentType(contentType);
		response.setHeader("Cache-Control", "no-store");
		try {
			if (!matchingPattern.isPresent()) {
				response.sendError(404);
			} else {
				router.get(matchingPattern.get())
				.handle(request, response);
			}
		} catch (ServletException e) {
			logger.error(e);
			
			try {
				response.sendError(500);
			} catch (IOException e2) {
				logger.error(e);
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	private Optional<String> findMatch(HttpServletRequest request) {
		return router.keySet().stream()
				.filter(pattern -> request.getRequestURI()
						.matches(pattern))
				.findFirst();
	}
}
