package daos;

import com.avaje.ebean.Model.Finder;
import models.SignupToken;

import java.util.Optional;

public class SignupTokenDAO {

	private Finder<Long, SignupToken> tokens = new Finder<>(SignupToken.class);

	public Optional<SignupToken> withCode(String code) {
		SignupToken signupToken = tokens.where().eq("code", code).findUnique();
		return Optional.ofNullable(signupToken);
	}

}
