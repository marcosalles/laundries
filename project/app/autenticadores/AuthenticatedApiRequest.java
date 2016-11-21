package autenticadores;

import daos.UserDAO;
import models.User;
import play.libs.Json;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthenticatedApiRequest extends Authenticator {

	@Inject
	private UserDAO userDAO;

	@Override
	public String getUsername(Context context) {
		String code = context.request().getHeader("API-Token");
		if ("1".equals(code)) { // FIXME remover
			return "1";
		}
		Optional<User> optionalUser = userDAO.withToken(code);
		if (optionalUser.isPresent()) {
			return optionalUser.get().getName();
		}
		return null;
	}

	@Override
	public Result onUnauthorized(Context context) {
		Map<String, String> errorParams = new HashMap<>();
		errorParams.put("status code", "401");
		errorParams.put("message", "unauthorized");
		Map<String, Object> errors = new HashMap<>();
		errors.put("errors", errorParams);
		return unauthorized(Json.toJson(errors));
	}
}
