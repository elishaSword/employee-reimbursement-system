package io.github.elishaSword.revature.training.project1.test.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.library.dao.UnsupportedDaoOperationException;
import io.github.elishaSword.library.passwordHash.PasswordHasher;
import io.github.elishaSword.library.trivial.passwordHash.TrivialPasswordHash;
import io.github.elishaSword.revature.training.project1.dao.EmployeeDao;
import io.github.elishaSword.revature.training.project1.models.employee.Employee;
import io.github.elishaSword.revature.training.project1.models.employee.Privilege;

public class EmployeeDaoTest {
	private static final Dao<String, Employee> dao = EmployeeDao.get();
	private static final List<Employee> initialEmployees = dao.getAll();
	private static final PasswordHasher hasher = TrivialPasswordHash.get();
	
	@Test
	public void insertTest() {
		final Employee t0 = new Employee("deputy2", hasher.hash("pass"), Privilege.FINANCE_MANAGER);
		final Employee t1 = new Employee("ace", hasher.hash("pass2"), Privilege.EMPLOYEE);
		
		final int oldSize = dao.getAll().size();
		
		insert(t0);
		insert(t1);
		
		final List<Employee> afterInsert = dao.getAll();

		assertEquals(oldSize + 2, afterInsert.size());
		assertTrue(afterInsert.contains(t0));
		assertTrue(afterInsert.contains(t1));
	}
	
	@Test
	public void updateTest() {
		final Employee t = new Employee("bee", hasher.hash("buzz"), Privilege.EMPLOYEE);
		
		final int oldSize = dao.getAll().size();
		insert(t);
		final List<Employee> afterInsert = dao.getAll();
		
		assertEquals(oldSize + 1, afterInsert.size());
		update(t.setPrivilege(Privilege.FINANCE_MANAGER));
		assertTrue(afterInsert.contains(new Employee("bee", hasher.hash("buzz"), Privilege.EMPLOYEE)));
	}
	
	@Test
	public void getTest() {
		final Employee t0 = new Employee("bird", hasher.hash("tweet"), Privilege.EMPLOYEE);
		final Employee t1 = new Employee("cat", hasher.hash("mow"), Privilege.EMPLOYEE);
		final Employee t2 = new Employee("not added", hasher.hash("not here"), Privilege.FINANCE_MANAGER);
		
		final int oldSize = dao.getAll().size();
		
		insert(t0);
		insert(t1);
		
		final List<Employee> afterInsert = dao.getAll();

		assertEquals(oldSize + 2, afterInsert.size());
		assertTrue(afterInsert.contains(t0));
		assertTrue(afterInsert.contains(t1));
		assertFalse(afterInsert.contains(t2));
	}
	
	@AfterAll
	public static void resetAndInDoingSoTestDelete() {
		System.out.println("clearing");

		for (Employee t : dao.getAll()) {
			if (!initialEmployees.contains(t)) {
				delete(t);
			}
		}

		final List<Employee> hopefullInitialEmployees = dao.getAll();

		assertEquals(initialEmployees.size(), hopefullInitialEmployees.size());
		for (Employee t : hopefullInitialEmployees) {
			assertTrue(initialEmployees.contains(t));
		}
	}
	
	private static void insert(Employee t) {
		dao.insert(t);
		
		assertTrue(dao.getAll().contains(t));
	}
	
	private static void update(Employee replacement) {
		if (dao.getById(replacement.getUsername()).isPresent()) {
			assertThrows(UnsupportedDaoOperationException.class, () -> dao.update(replacement));
		}
	}
	
	private static void delete(Employee t) {
		dao.delete(t);
		
		assertFalse(dao.getAll().contains(t));
	}
}
