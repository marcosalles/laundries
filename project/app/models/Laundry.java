package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	private List<Machine> machines;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setMachines(List<Machine> machines) {
		this.machines = machines;
	}
}
