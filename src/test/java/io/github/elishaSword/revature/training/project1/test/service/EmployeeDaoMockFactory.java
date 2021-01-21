package io.github.elishaSword.revature.training.project1.test.service;

import static org.mockito.AdditionalAnswers.answer;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.stubbing.Answer;

import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.library.passwordHash.PasswordHasher;
import io.github.elishaSword.library.trivial.passwordHash.TrivialPasswordHash;
import io.github.elishaSword.revature.training.project1.dao.EmployeeDao;
import io.github.elishaSword.revature.training.project1.models.employee.Employee;
import io.github.elishaSword.revature.training.project1.models.employee.Privilege;

public class EmployeeDaoMockFactory {
	private static final Dao<String, Employee> dao = mock(EmployeeDao.class);
	private static final List<Employee> allEmployees = new ArrayList<Employee>();
	private static final PasswordHasher hasher = TrivialPasswordHash.get();
	private static final Employee deputy = new Employee("deputy", hasher.hash("password"), Privilege.FINANCE_MANAGER);
	private static final Employee alex = new Employee("alex", hasher.hash("password1"), Privilege.EMPLOYEE);

	private EmployeeDaoMockFactory() {
		super();
	}
	
	public static Dao<String, Employee> get() {
		allEmployees.add(deputy);
		allEmployees.add(alex);

		when(dao.getAll())
			.thenReturn(allEmployees);
	
		doAnswer(answer(input -> allEmployees.stream()
					.filter(e -> e.getUsername().equals(input))
					.findFirst()
		)).when(dao).getById(anyString());
		
		doAnswer((Answer<Employee>) (invocation) -> {
			allEmployees.add(invocation.getArgument(0));
			return null;
		}).when(dao).insert(isA(Employee.class));
		
		return dao;
	}
}
