package io.github.elishaSword.revature.training.project1.models.ticket;

import java.sql.Date;
import java.util.Objects;

public class Ticket {
	private int id;
	private String employeeUsername;
	private TicketType type;
	private String description;
	private TicketStatus status; 
	private Date timestamp;
	private int price;
	
	public Ticket() {
		super();
	}
	public Ticket(int id, String employeeUsername, TicketType type, String description, TicketStatus status, Date timestamp, int price) {
		this.id = id;
		this.employeeUsername = employeeUsername;
		this.type = type;
		this.description = description;
		this.status = status;
		this.timestamp = timestamp;
		this.price = price;
	}

	public int getId() {
		return id;
	}
	public Ticket setId(int id) {
		this.id = id;
		
		return this;
	}
	public String getEmployeeUsername() {
		return employeeUsername;
	}
	public Ticket setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
		
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Ticket setDescription(String description) {
		this.description = description;
		
		return this;
	}
	public TicketType getType() {
		return type;
	}
	public Ticket setType(TicketType type) {
		this.type = type;
		
		return this;
	}
	public TicketStatus getStatus() {
		return status;
	}
	public Ticket setStatus(TicketStatus status) {
		this.status = status;
		
		return this;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public Ticket setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
		
		return this;
	}
	public int getPrice() {
		return price;
	}
	public Ticket setPrice(int price) {
		this.price = price;
		
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, employeeUsername, id, price, status, timestamp, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Ticket)) {
			return false;
		}
		Ticket other = (Ticket) obj;
		return Objects.equals(description, other.description)
				&& Objects.equals(employeeUsername, other.employeeUsername) && id == other.id && price == other.price
				&& status == other.status && Objects.equals(timestamp, other.timestamp) && type == other.type;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", employeeUsername=" + employeeUsername + ", type=" + type + ", description="
				+ description + ", status=" + status + ", timestamp=" + timestamp + ", price=" + price + "]";
	}
	
	
}
