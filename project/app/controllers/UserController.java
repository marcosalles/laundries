package controllers;

import akka.util.Crypt;
import authenticators.AuthenticatedCustomer;
import daos.SignupTokenDAO;
import daos.UserDAO;
import models.ApiToken;
import models.SignupEmail;
import models.SignupToken;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import validators.UserValidator;
import views.html.*;

import javax.inject.Inject;
import java.util.Optional;

public class UserController extends Controller {

	public static final String AUTH = "AUTH";
	public static final String SALT = "lavanduino";
	@Inject
	private FormFactory forms;
	@Inject
	private UserValidator userValidator;
	@Inject
	private MailerClient mailer;
	@Inject
	private UserDAO users;
	@Inject
	private SignupTokenDAO signupTokens;

	public Result newUserForm() {
		Form<User> form = forms.form(User.class);
		return ok(newUserForm.render(form));
	}

	public Result saveNewUser() {
		Form<User> form = forms.form(User.class).bindFromRequest();
		if (userValidator.hasErrors(form)) {
			flash("danger", "There are errors in your form.");
			return badRequest(newUserForm.render(form));
		}
		User user = form.get();
		String cryptoPass = Crypt.sha1(SALT+user.getPassword());
		user.setPassword(cryptoPass);
		user.save();
		SignupToken token = new SignupToken(user);
		token.save();
		mailer.send(new SignupEmail(token));
		flash("success", "An email has been sent to your address. Please click the link to verify.");
		return redirect(routes.UserController.loginForm());
	}

	public Result verifyUser(String email, String code) {
		Optional<User> optionalUser = users.withEmail(email);
		Optional<SignupToken> optionalToken = signupTokens.withCode(code);
		if (optionalToken.isPresent() && optionalUser.isPresent()) {
			SignupToken token = optionalToken.get();
			User user = optionalUser.get();
			if (token.getUser().equals(user)) {
				token.delete();
				user.setVerified(true);
				ApiToken apiToken = new ApiToken(user);
				apiToken.save();
				user.setToken(apiToken);
				user.update();
				flash("success", "Your account has been verified!");
				putUserInSession(user);
				return redirect(routes.UserController.dashboard());
			}
		}
		flash("danger", "An error occurred while trying to verify your account!");
		return redirect(routes.UserController.loginForm());
	}

	private void putUserInSession(User user) {
		session(AUTH, user.getToken().getCode());
	}

	public Result loginForm() {
		return ok(loginForm.render(forms.form()));
	}

	public Result login() {
		DynamicForm form = forms.form().bindFromRequest();
		String email = form.get("email");
		String password = Crypt.sha1(SALT+form.get("password"));
		Optional<User> optionalUser = users.withCredentials(email, password);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.isVerified()) {
				putUserInSession(user);
				return redirect(routes.UserController.dashboard());
			} else {
				flash("warning", "Account not verified. Please check your email and follow the instructions!");
			}
		} else {
			flash("danger", "Invalid credentials!");
		}
		return redirect(routes.UserController.loginForm());
	}

	@Authenticated(AuthenticatedCustomer.class)
	public Result dashboard() {
		String code = session(AUTH);
		User user = users.withToken(code).get();
		return ok(dashboard.render(user));
	}

	@Authenticated(AuthenticatedCustomer.class)
	public Result logout() {
		session().clear();
		flash("success", "Logged out successfully!");
		return redirect(routes.UserController.loginForm());
	}

}
