package validators;

import javax.inject.Inject;

import daos.UserDAO;
import models.User;
import play.data.Form;
import play.data.validation.ValidationError;

public class UserValidator {

	@Inject
	private UserDAO userDAO;
	
	public boolean hasErrors(Form<User> form) {
		validPassword(form);
		validEmail(form);
		return form.hasErrors();
	}

private void validEmail(Form<User> form) {
		String email = form.field("email").valueOr("");
		if (userDAO.withEmail(email).isPresent()) {
			form.reject(new ValidationError("email", "This email has already been taken."));
		}
	}

	private void validPassword(Form<User> form) {
		String password = form.field("password").valueOr("");
		String passwordConfirmation = form.field("passwordConfirmation").valueOr("");
		if (passwordConfirmation.isEmpty()) {
			form.reject(new ValidationError("passwordConfirmation", "You must verify your password!"));
		}
		else if (!password.equals(passwordConfirmation)) {
			form.reject(new ValidationError("password", ""));
			form.reject(new ValidationError("passwordConfirmation", "Both passwords must be the same!"));
		}
	}

}
