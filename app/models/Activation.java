package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
public class Activation extends Model {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private User user;
	@ManyToOne
	private Machine machine;
	private BigDecimal pricePaid;
	private LocalDateTime date;

	public Activation(User user, Machine machine) {
		this.user = user;
		this.machine = machine;
		this.date = LocalDateTime.now();
		this.pricePaid = machine.getUsePrice();
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Machine getMachine() {
		return machine;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public BigDecimal getPricePaid() {
		return pricePaid;
	}
}
