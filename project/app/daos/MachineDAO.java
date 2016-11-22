package daos;

import com.avaje.ebean.Model.Finder;
import models.Machine;

import java.util.Optional;

public class MachineDAO {
	private Finder<Long, Machine> machines = new Finder<>(Machine.class);

	public Optional<Machine> withId(String id) {
		Machine machine = machines.where().eq("id", id).findUnique();
		return Optional.ofNullable(machine);
	}
}
