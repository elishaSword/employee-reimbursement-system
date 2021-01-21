package io.github.elishaSword.revature.training.project1.test.baseTypes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.elishaSword.library.controller.EndpointHandler;
import io.github.elishaSword.library.controller.Router;

class RouterTest {
	private static final Router router = new Router("text/plain");

	private static final EndpointHandler handler = mock(EndpointHandler.class);

	final static HttpServletRequest request0 = mock(HttpServletRequest.class);
	final static HttpServletRequest request1 = mock(HttpServletRequest.class);
	final static HttpServletRequest request2 = mock(HttpServletRequest.class);
	final static HttpServletRequest request3 = mock(HttpServletRequest.class);
	final static HttpServletRequest request4 = mock(HttpServletRequest.class);
	final static HttpServletRequest request5 = mock(HttpServletRequest.class);
	final static HttpServletRequest request6 = mock(HttpServletRequest.class);
	final static HttpServletRequest request7 = mock(HttpServletRequest.class);
	final static HttpServletRequest request8 = mock(HttpServletRequest.class);
	final static HttpServletRequest request9 = mock(HttpServletRequest.class);
	final static HttpServletRequest request10 = mock(HttpServletRequest.class);
	final static HttpServletRequest request11 = mock(HttpServletRequest.class);
	final static HttpServletRequest request12 = mock(HttpServletRequest.class);
	
	@BeforeAll
	public static void setup() {
		router.put("/test", handler);
		router.put("/apple/?", handler);
		router.put("/user/[0-9]+/?", handler);

		when(request0.getRequestURI())
			.thenReturn("");
		when(request1.getRequestURI())
			.thenReturn("/test");
		when(request2.getRequestURI())
			.thenReturn("/test/");
		when(request3.getRequestURI())
			.thenReturn("/apple");
		when(request4.getRequestURI())
			.thenReturn("/apple/");
		when(request5.getRequestURI())
			.thenReturn("/apple/a");
		when(request6.getRequestURI())
			.thenReturn("/user");
		when(request7.getRequestURI())
			.thenReturn("/user/");
		when(request8.getRequestURI())
			.thenReturn("/user/3");
		when(request9.getRequestURI())
			.thenReturn("/user/3/");
		when(request10.getRequestURI())
			.thenReturn("/user/34");
		when(request11.getRequestURI())
			.thenReturn("/user/34/");
		when(request12.getRequestURI())
			.thenReturn("/user/34/a");
	}

	@Test
	void support() {
		assertFalse(router.supports(request0));
		assertTrue(router.supports(request1));
		assertFalse(router.supports(request2));

		assertTrue(router.supports(request3));
		assertTrue(router.supports(request4));
		assertFalse(router.supports(request5));
		
		assertFalse(router.supports(request6));
		assertFalse(router.supports(request7));
		assertTrue(router.supports(request8));
		assertTrue(router.supports(request9));
		assertTrue(router.supports(request10));
		assertTrue(router.supports(request11));
		assertFalse(router.supports(request12));
	}
	
	@Test
	void handleGood() throws ServletException, IOException {
		final HttpServletResponse response = mock(HttpServletResponse.class);
		
		assertTrue(router.supports(request11));
		router.handle(request11, response);

		verify(response, never())
			.sendError(404);
	}

	@Test
	void handleBad() throws ServletException, IOException {
		final HttpServletResponse response = mock(HttpServletResponse.class);
		
		assertFalse(router.supports(request12));
		router.handle(request12, response);
		
		verify(response, times(1))
			.sendError(404);
	}

}
