package daos;

import com.avaje.ebean.Model.Finder;
import models.User;

import java.util.List;
import java.util.Optional;

public class UserDAO {

	private Finder<Long, User> users = new Finder<>(User.class);

	public Optional<User> withEmail(String email) {
		User user = users.where().eq("email", email).findUnique();
		return Optional.ofNullable(user);
	}

	public Optional<User> withCredentials(String email, String password) {
		User user = users.where().eq("email", email).eq("password", password).findUnique();
		return Optional.ofNullable(user);
	}

	public Optional<User> withToken(String code) {
		User user = users.where().eq("token.code", code).findUnique();
		return Optional.ofNullable(user);
	}

	public List<User> all() {
		return users.all();
	}

	public Optional<User> withId(Long id) {
		return Optional.ofNullable(users.byId(id));
	}
}
