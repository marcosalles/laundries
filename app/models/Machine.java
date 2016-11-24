package models;

import akka.util.Crypt;
import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Machine extends Model {

	@Id
	private String id;
	@ManyToOne
	private Laundry laundry;
	@Enumerated(EnumType.STRING)
	private Type type;
	@Column(precision = 10, scale = 2)
	private BigDecimal usePrice;
	private int cycleDuration;
	private LocalDateTime activeUntil;

	public Machine() {
		this.id = Crypt.sha1(Crypt.generateSecureCookie() + LocalDateTime.now().toString());
	}

	public Machine(Type type) {
		this();
		this.type = type;
		this.usePrice = new BigDecimal(10.0);
		this.cycleDuration = 5;
	}

	public Machine(Laundry laundry, Type type, BigDecimal usePrice, int cycleDuration) {
		this();
		this.laundry = laundry;
		this.type = type;
		this.usePrice = usePrice;
		this.cycleDuration = cycleDuration;
	}

	public String getId() {
		return id;
	}

	public Laundry getLaundry() {
		return laundry;
	}

	public void setLaundry(Laundry laundry) {
		this.laundry = laundry;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public BigDecimal getUsePrice() {
		return usePrice;
	}

	public void setUsePrice(BigDecimal usePrice) {
		this.usePrice = usePrice;
	}

	public int getCycleDuration() {
		return cycleDuration;
	}

	public void setCycleDuration(int cycleDuration) {
		this.cycleDuration = cycleDuration;
	}

	public LocalDateTime getActiveUntil() {
		return activeUntil;
	}

	public boolean activate() {
		if (isActive()) {
			return false;
		}
		activeUntil = LocalDateTime.now().plusMinutes(cycleDuration);
		return true;
	}

	public boolean isActive() {
		return activeUntil == null || LocalDateTime.now().isBefore(activeUntil);
	}
}
