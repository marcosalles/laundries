package daos;

import com.avaje.ebean.Model.Finder;
import models.Laundry;

import java.util.List;
import java.util.Optional;

public class LaundryDAO {

	private Finder<Long, Laundry> laundries = new Finder<>(Laundry.class);

	public Optional<Laundry> withName(String name) {
		Laundry laundry = laundries
				.where()
				.eq("name", name)
				.findUnique();
		return Optional.ofNullable(laundry);
	}

	public List<Laundry> all() {
		return laundries.all();
	}

}
