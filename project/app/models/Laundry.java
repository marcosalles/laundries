package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Laundry extends Model {

	@Id
	@GeneratedValue
	private Long id;
	@Required(message = "Required field")
	private String name;
	@Required(message = "Required field")
	private String address;
	@OneToMany(mappedBy = "laundry")
	private List<Machine> machines = new ArrayList<>();

	public Laundry(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Machine> getMachines() {
		return machines;
	}

	public boolean delete() {
		getMachines().forEach(m -> {
			m.setLaundry(null);
			m.update();
		});
		return super.delete();
	}
}
