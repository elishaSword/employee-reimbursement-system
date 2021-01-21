package io.github.elishaSword.revature.training.project1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.elishaSword.library.dao.Dao;
import io.github.elishaSword.library.dao.utilities.ConnectionFactory;
import io.github.elishaSword.revature.training.project1.models.ticket.Ticket;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketStatus;
import io.github.elishaSword.revature.training.project1.models.ticket.TicketType;

public class TicketDao implements Dao<Integer, Ticket> {
	private static final Dao<Integer, Ticket> dao = new TicketDao();
	private static final Logger logger = LogManager.getLogger();
	
	private TicketDao() {
		super();
	}

	public static Dao<Integer, Ticket> get() {
		return dao;
	}

	@Override
	public void insert(Ticket value) {
		logger.debug("INSERT {}", value);
		try (Connection connection = ConnectionFactory.get()) {
			try (PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO ers_ticket (creation_time, employee_username, expense_type, description, status, amount) VALUES "
						+ "(?, ?, ?, ?, ?, ?)"))
			{
				statement.setDate(1, value.getTimestamp());
				statement.setString(2, value.getEmployeeUsername());
				statement.setInt(3, value.getType().toInt());
				statement.setString(4, value.getDescription());
				statement.setInt(5, value.getStatus().toInt());
				statement.setInt(6, value.getPrice());

				statement.execute();
			}
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public Optional<Ticket> getById(Integer key) {
		Optional<Ticket> output;
		
		logger.debug("SELECT {}", key);
		try (Connection connection = ConnectionFactory.get()) {
			try(PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM ers_ticket WHERE id = ?"))
			{
				statement.setInt(1, key.intValue());

				ResultSet results = statement.executeQuery();
				if (!results.next()) {
					output = Optional.empty();
				} else {
					output = Optional.of(new Ticket(
							results.getInt(1),
							results.getString(3),
							TicketType.fromInt(results.getInt(4))
								.get(),
							results.getString(6),
							TicketStatus.fromInt(results.getInt(7))
								.get(),
							results.getDate(2),
							results.getInt(5)));
				}
			}
		} catch (SQLException e) {
			output = Optional.empty();
			logger.error(e);
		}
		
		return output;
	}

	@Override
	public List<Ticket> getAll() {
		List<Ticket> output = new ArrayList<>();
		
		logger.debug("SELECT");
		try (Connection connection = ConnectionFactory.get()) {
			try(PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM ers_ticket ORDER BY id ASC"))
			{
				ResultSet results = statement.executeQuery();
				while (results.next()) {
					output.add(new Ticket(
							results.getInt(1),
							results.getString(3),
							TicketType.fromInt(results.getInt(4))
								.get(),
							results.getString(6),
							TicketStatus.fromInt(results.getInt(7))
								.get(),
							results.getDate(2),
							results.getInt(5)));
				}
			}
		} catch (SQLException e) {
			output = new ArrayList<>();
			logger.error(e);
		}
		
		return output;
	}

	/**
	 * the only update we support is status changes!
	 */
	@Override
	public void update(Ticket value) {
		logger.debug("UPDATE {}", value);
		try (Connection connection = ConnectionFactory.get()) {
			try (PreparedStatement statement = connection.prepareStatement(
					"UPDATE ers_ticket SET status = ? WHERE id = ?"))
			{
				statement.setInt(1, value.getStatus().toInt());
				statement.setInt(2, value.getId());

				statement.execute();
			}
		} catch(SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public void delete(Ticket value) {
		logger.debug("DELETE {}", value);
		try (Connection connection = ConnectionFactory.get()) {
			try(PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM ers_ticket "
						+ "WHERE id = ? AND creation_time = ? AND employee_username = ? AND expense_type = ? AND description = ? AND status = ? AND amount = ?"))
			{
				statement.setInt(1, value.getId());
				statement.setDate(2, value.getTimestamp());
				statement.setString(3,  value.getEmployeeUsername());
				statement.setInt(4, value.getType()
						.toInt());
				statement.setString(5, value.getDescription());
				statement.setInt(6, value.getStatus()
						.toInt());
				statement.setInt(7, value.getPrice());

				statement.execute();
			}
		} catch(SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public void deleteById(Integer key) {
		logger.debug("DELETE WHERE id = {}", key);
		try (Connection connection = ConnectionFactory.get()) {
			try (PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM ers_ticket WHERE id = ?"))
			{
				statement.setInt(1, key);
			
				statement.execute();
			}
		} catch(SQLException e) {
			logger.error(e);
		}
	}

}
