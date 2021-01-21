package io.github.elishaSword.revature.training.project1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.library.dao.UnsupportedDaoOperationException;
import io.github.elishaSword.library.dao.utilities.ConnectionFactory;
import io.github.elishaSword.library.trivial.passwordHash.TrivialPasswordHash;
import io.github.elishaSword.revature.training.project1.models.employee.Employee;
import io.github.elishaSword.revature.training.project1.models.employee.Privilege;

public class EmployeeDao implements Dao<String, Employee> {
	private static final Dao<String, Employee> dao = new EmployeeDao();
	private static final Logger logger = LogManager.getLogger();
	
	private EmployeeDao() {
		super();
	}
	
	public static Dao<String, Employee> get() {
		return dao;
	}

	@Override
	public void insert(Employee value) {
		logger.debug("INSERT {}", value);
		try (Connection connection = ConnectionFactory.get()) {
			try (final PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO ers_employee VALUES (?, ?, ?)"))
			{
				statement.setString(1, value.getUsername());
				statement.setString(2, value.getPassword().getHashedPassword());
				statement.setInt(3, value.getPrivilege().toInt());

				statement.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Optional<Employee> getById(String key) {
		Optional<Employee> output;
		
		logger.debug("SELECT {}", key);
		try (Connection connection = ConnectionFactory.get()) {
			try (PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM ers_employee WHERE username = ?"))
			{
				statement.setString(1, key);

				ResultSet results = statement.executeQuery();
				if (results.next()) {
					output = Optional.of(new Employee(
							results.getString(1),
							TrivialPasswordHash.get()
								.hash(results.getString(2)),
							Privilege.fromInt(results.getInt(3))
								.get()));
				} else {
					output = Optional.empty();
				}
			}
		} catch(SQLException e) {
			output = Optional.empty();
			e.printStackTrace();
		}
		
		return output;
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> output = new ArrayList<>();
		
		logger.debug("SELECT");
		try (Connection connection = ConnectionFactory.get()) {
			try (PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM ers_employee"))
			{
				ResultSet results = statement.executeQuery();
				while (results.next()) {
					output.add(new Employee(
							results.getString(1),
							TrivialPasswordHash.get()
								.hash(results.getString(2)),
							Privilege.fromInt(results.getInt(3))
								.get()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}

	@Override
	public void update(Employee value) {
		throw new UnsupportedDaoOperationException("update");
	}

	@Override
	public void delete(Employee value) {
		logger.debug("DELETE {}", value);
		try (Connection connection = ConnectionFactory.get()) {
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM ers_employee WHERE username = ? AND password = ? AND privilege = ?");
			statement.setString(1, value.getUsername());
			statement.setString(2, value.getPassword().getHashedPassword());
			statement.setInt(3, value.getPrivilege().toInt());
			
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(String key) {
		logger.debug("DELETE WHERE username = {}", key);
		try (Connection connection = ConnectionFactory.get()) {
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM ers_employee WHERE username = ?");
			statement.setString(1, key);
			
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
