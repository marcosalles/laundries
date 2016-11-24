package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment extends Model {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private User user;
	@Column(precision = 20, scale = 2)
	private BigDecimal value;
	private LocalDateTime date;

	public Payment(User user, Double value) {
		this.user = user;
		this.value = new BigDecimal(value);
		this.date = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public BigDecimal getValue() {
		return value;
	}

	public LocalDateTime getDate() {
		return date;
	}

}
