package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Machine extends Model {

	@Id
	private final String id;
	@OneToOne
	private Laundry laundry;
	@Enumerated(EnumType.STRING)
	private Type type;
	private BigDecimal usePrice;
	private int cycleDuration;
	private Date activeUntil;

	public Machine(String id) {
		this.id = id;
	}

	public Machine(String id, Laundry laundry, Type type, BigDecimal usePrice, int cycleDuration) {
		this(id);
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

	public Date getActiveUntil() {
		return activeUntil;
	}

	public void setActiveUntil(Date activeUntil) {
		this.activeUntil = activeUntil;
	}
}
