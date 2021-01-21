package io.github.elishaSword.revature.training.project1.test.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.library.passwordHash.PasswordHasher;
import io.github.elishaSword.library.trivial.passwordHash.TrivialPasswordHash;
import io.github.elishaSword.revature.training.project1.dao.EmployeeDao;
import io.github.elishaSword.revature.training.project1.models.employee.Employee;
import io.github.elishaSword.revature.training.project1.models.employee.Privilege;
import io.github.elishaSword.revature.training.project1.service.EmployeeService;

class EmployeeServiceTest {
	private static final Dao<String, Employee> dao = EmployeeDaoMockFactory.get();
	private static final EmployeeService service = new EmployeeService(dao);
	private static final PasswordHasher hasher = TrivialPasswordHash.get();
	
	@Test
	void register() {
		Employee e0 = new Employee("arrow", hasher.hash("pass"), Privilege.EMPLOYEE);
		
		assertTrue(service.register(e0));
		for (int i = 0; i < 100; ++i) {
			assertFalse(service.register(e0)); // already contained
		}

		for (Employee e : dao.getAll()) {
			for (int i = 0; i < 100; ++i) {
				assertFalse(service.register(e));
			}
		}
	}
	
	@Test
	void verifyCredentials() {
		final String unseenUsername = dao.getAll().stream()
				.map(e -> e.getUsername())
				.reduce("", (l, r) -> l + r);
		final String unseenPassword = dao.getAll().stream()
				.map(e -> e.getPassword().getHashedPassword())
				.reduce("", (l, r) -> l + r);
		
		for (Employee e : dao.getAll()) {
			assertTrue(service.verifyCredentials(e.getUsername(), e.getPassword()).isPresent());
			
			assertFalse(service.verifyCredentials(unseenUsername, e.getPassword()).isPresent());
			assertFalse(service.verifyCredentials(e.getUsername(), hasher.hash(unseenPassword)).isPresent());
		}
		assertFalse(service.verifyCredentials(unseenUsername, hasher.hash(unseenPassword)).isPresent());
	}
	
	@Test
	void verifyFromActualDatabase() {
		EmployeeService ec2Service = new EmployeeService(EmployeeDao.get());

		assertTrue(ec2Service.verifyCredentials("alex", hasher.hash("password1")).isPresent());
		assertTrue(ec2Service.verifyCredentials("deputy", hasher.hash("password")).isPresent());
	}
}
