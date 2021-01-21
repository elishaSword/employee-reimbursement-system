package io.github.elishaSword.revature.training.project1.models.employee;

import java.util.Objects;

import io.github.elishaSword.library.passwordHash.HashedPassword;

public class Employee {
	private String username;
	private HashedPassword password;
	private Privilege privilege;
	
	public Employee() {
		super();
	}
	public Employee(String username, HashedPassword password, Privilege privilege) {
		this.username = username;
		this.password = password;
		this.privilege = privilege;
	}

	public String getUsername() {
		return username;
	}
	public Employee setUsername(String username) {
		this.username = username;
		
		return this;
	}
	public HashedPassword getPassword() {
		return password;
	}
	public Employee setPassword(HashedPassword password) {
		this.password = password;
		
		return this;
	}
	public Privilege getPrivilege() {
		return privilege;
	}
	public Employee setPrivilege(Privilege privilege) {
		this.privilege = privilege;
		
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, privilege, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Employee)) {
			return false;
		}
		Employee other = (Employee) obj;
		return Objects.equals(password, other.password) && privilege == other.privilege
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Employee [username=" + username + ", password=" + password + ", privilege=" + privilege + "]";
	}
}
