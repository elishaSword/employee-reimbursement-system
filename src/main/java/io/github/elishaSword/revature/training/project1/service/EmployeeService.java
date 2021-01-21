package io.github.elishaSword.revature.training.project1.service;

import java.util.Optional;

import io.github.elishaSword.library.passwordHash.HashedPassword;
import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.revature.training.project1.models.employee.Employee;

public class EmployeeService {
	private final Dao<String, Employee> dao;
	
	public EmployeeService(Dao<String, Employee> dao) {
		this.dao = dao;
	}
	
	public Optional<Employee> getEmployeeByUsername(String username) {
		return dao.getById(username);
	}
	
	public Optional<Employee> verifyCredentials(String key, HashedPassword password) {
		return dao.getById(key)
				.filter(e -> e.getPassword().equals(password));
	}
	
	public boolean register(Employee employee) {
		final boolean output = !dao.getAll().contains(employee);
		
		if (output) {
			dao.insert(employee);

			emailNewEmployee(employee);
		}
		
		return output;
	}
	
	private void emailNewEmployee(Employee employee) {
		// TODO (stretch goal): implement
	}
}
