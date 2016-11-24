package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Activation extends Model {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private User user;
	@OneToOne
	private Machine machine;
	private LocalDateTime date;

	public Activation(User user, Machine machine) {
		this.user = user;
		this.machine = machine;
		this.date = LocalDateTime.now();
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

}
