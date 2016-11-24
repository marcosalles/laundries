package authenticators;

import controllers.routes;
import daos.UserDAO;
import models.User;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

import javax.inject.Inject;
import java.util.Optional;

import static controllers.UserController.AUTH;

public class AuthenticatedAdmin extends Authenticator {

	@Inject
	private UserDAO userDAO;

	@Override
	public String getUsername(Context context) {
		String code = context.session().get(AUTH);
		Optional<User> optionalUser = userDAO.withToken(code);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.isAdmin()) {
				context.args.put("user", user);
				return user.getName();
			}
		}
		return null;
	}

	@Override
	public Result onUnauthorized(Context context) {
		return redirect(routes.UserController.dashboard());
	}
}
