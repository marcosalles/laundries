package autenticadores;

import static controllers.UserController.AUTH;

import java.util.Optional;

import javax.inject.Inject;

import controllers.routes;
import daos.UserDAO;
import models.User;
import play.mvc.Result;
import play.mvc.Http.Context;
import play.mvc.Security.Authenticator;

public class AuthenticatedCustomer extends Authenticator {

	@Inject
	private UserDAO userDAO;
	@Override
	public String getUsername(Context context) {
		String code = context.session().get(AUTH);
		Optional<User> optionalUser = userDAO.withToken(code);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			context.args.put("user", user);
			return user.getName();
		}
		return null;
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		context.flash().put("danger", "Unauthorized access!");
		return redirect(routes.UserController.loginForm());
	}
}
