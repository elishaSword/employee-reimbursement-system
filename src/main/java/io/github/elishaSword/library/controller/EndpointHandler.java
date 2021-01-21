package io.github.elishaSword.library.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.elishaSword.library.servlet.ServletHandler;

public class EndpointHandler {
	private final Map<String, ServletHandler> httpMethodHandlers = new HashMap<String, ServletHandler>();
	private boolean requiresSession = false;
	private String sessionGetter;
	
	/**
	 * implements a trivial HEAD handler and requires a GET handler, as both are required by HTTP standards
	 * @param getHandler the handler for GET requests
	 */
	public EndpointHandler(ServletHandler getHandler) {
		httpMethodHandlers.put("HEAD", (request, response) -> {
			response.setStatus(200);
		});
		
		httpMethodHandlers.put("GET", getHandler);
	}
	
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (httpMethodHandlers.containsKey(request.getMethod())) {
			ServletHandler handler = httpMethodHandlers.get(request.getMethod());
			
			if (requiresSession) {
				handler.redirectIfSessionless(sessionGetter)
					.accept(request, response);
			} else {
				handler.accept(request, response);
			}
		} else {
			response.sendError(405);
		}
	}
	
	public EndpointHandler allowPost(ServletHandler handler) {
		httpMethodHandlers.put("POST", handler);
		
		return this;
	}
	
	public EndpointHandler allowPut(ServletHandler handler) {
		httpMethodHandlers.put("PUT", handler);
		
		return this;
	}
	
	public EndpointHandler allowDelete(ServletHandler handler) {
		httpMethodHandlers.put("DELETE", handler);
		
		return this;
	}
	
	public EndpointHandler setRequiresSession(String sessionGetter) {
		requiresSession = true;
		this.sessionGetter = sessionGetter;
		
		return this;
	}
}