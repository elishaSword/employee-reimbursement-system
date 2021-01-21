package io.github.elishaSword.revature.training.project1.test.baseTypes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;

import io.github.elishaSword.library.controller.EndpointHandler;
import io.github.elishaSword.library.servlet.ServletHandler;

class EndpointHandlerTest {

	@Test
	void handleDefault() throws ServletException, IOException {
		final EndpointHandler endpointHandler = new EndpointHandler(mock(ServletHandler.class));

		final HttpServletRequest request0 = mock(HttpServletRequest.class);
		final HttpServletRequest request1 = mock(HttpServletRequest.class);
		final HttpServletRequest request2 = mock(HttpServletRequest.class);
		final HttpServletRequest request3 = mock(HttpServletRequest.class);
		final HttpServletRequest request4 = mock(HttpServletRequest.class);
		
		when(request0.getMethod())
			.thenReturn("HEAD");
		when(request1.getMethod())
			.thenReturn("GET");
		when(request2.getMethod())
			.thenReturn("POST");
		when(request3.getMethod())
			.thenReturn("PUT");
		when(request4.getMethod())
			.thenReturn("DELETE");

		final HttpServletResponse response0 = mock(HttpServletResponse.class);
		final HttpServletResponse response1 = mock(HttpServletResponse.class);
		final HttpServletResponse response2 = mock(HttpServletResponse.class);
		final HttpServletResponse response3 = mock(HttpServletResponse.class);
		final HttpServletResponse response4 = mock(HttpServletResponse.class);
		
		endpointHandler.handle(request0, response0);
		endpointHandler.handle(request1, response1);
		endpointHandler.handle(request2, response2);
		endpointHandler.handle(request3, response3);
		endpointHandler.handle(request4, response4);
		
		verify(response0, never())
			.sendError(405);
		verify(response1, never())
			.sendError(405);

		verify(response2, times(1))
			.sendError(405);
		verify(response3, times(1))
			.sendError(405);
		verify(response4, times(1))
			.sendError(405);
	}
	
	@Test
	void handlePost() throws ServletException, IOException {
		final EndpointHandler endpointHandler = new EndpointHandler(mock(ServletHandler.class));
		
		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod())
			.thenReturn("POST");
		
		final HttpServletResponse response0 = mock(HttpServletResponse.class);
		final HttpServletResponse response1 = mock(HttpServletResponse.class);
		
		endpointHandler.handle(request, response0);
		verify(response0, times(1))
			.sendError(405);
		
		endpointHandler.allowPost(mock(ServletHandler.class))
			.handle(request, response1);
		verify(response1, never())
			.sendError(405);
	}
	
	@Test
	void handlePut() throws ServletException, IOException {
		final EndpointHandler endpointHandler = new EndpointHandler(mock(ServletHandler.class));
		
		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod())
			.thenReturn("PUT");
		
		final HttpServletResponse response0 = mock(HttpServletResponse.class);
		final HttpServletResponse response1 = mock(HttpServletResponse.class);
		
		endpointHandler.handle(request, response0);
		verify(response0, times(1))
			.sendError(405);
		
		endpointHandler.allowPut(mock(ServletHandler.class))
			.handle(request, response1);
		verify(response1, never())
			.sendError(405);
	}
	
	@Test
	void handleDelete() throws ServletException, IOException {
		final EndpointHandler endpointHandler = new EndpointHandler(mock(ServletHandler.class));
		
		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod())
			.thenReturn("DELETE");
		
		final HttpServletResponse response0 = mock(HttpServletResponse.class);
		final HttpServletResponse response1 = mock(HttpServletResponse.class);
		
		endpointHandler.handle(request, response0);
		verify(response0, times(1))
			.sendError(405);
		
		endpointHandler.allowDelete(mock(ServletHandler.class))
			.handle(request, response1);
		verify(response1, never())
			.sendError(405);
	}
	
	@Test
	void handleMultiple() throws ServletException, IOException {
		final EndpointHandler endpointHandler = new EndpointHandler(mock(ServletHandler.class))
				.allowPost(mock(ServletHandler.class))
				.allowPut(mock(ServletHandler.class));

		final HttpServletRequest request0 = mock(HttpServletRequest.class);
		final HttpServletRequest request1 = mock(HttpServletRequest.class);
		final HttpServletRequest request2 = mock(HttpServletRequest.class);
		final HttpServletRequest request3 = mock(HttpServletRequest.class);
		final HttpServletRequest request4 = mock(HttpServletRequest.class);
		
		when(request0.getMethod())
			.thenReturn("HEAD");
		when(request1.getMethod())
			.thenReturn("GET");
		when(request2.getMethod())
			.thenReturn("POST");
		when(request3.getMethod())
			.thenReturn("PUT");
		when(request4.getMethod())
			.thenReturn("DELETE");

		final HttpServletResponse response0 = mock(HttpServletResponse.class);
		final HttpServletResponse response1 = mock(HttpServletResponse.class);
		final HttpServletResponse response2 = mock(HttpServletResponse.class);
		final HttpServletResponse response3 = mock(HttpServletResponse.class);
		final HttpServletResponse response4 = mock(HttpServletResponse.class);
		
		endpointHandler.handle(request0, response0);
		endpointHandler.handle(request1, response1);
		endpointHandler.handle(request2, response2);
		endpointHandler.handle(request3, response3);
		endpointHandler.handle(request4, response4);
		
		verify(response0, never())
			.sendError(405);
		verify(response1, never())
			.sendError(405);
		verify(response2, never())
			.sendError(405);
		verify(response3, never())
			.sendError(405);

		verify(response4, times(1))
			.sendError(405);
	}
}
