package authenticators;

import daos.UserDAO;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
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
	private FormFactory forms;
	@Inject
	private UserDAO userDAO;

	@Override
	public String getUsername(Context context) {
		DynamicForm form = forms.form().bindFromRequest(context.request());
		String code = form.field("qrcode").value();
		Optional<User> optionalUser = userDAO.withToken(code);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.isVerified()) {
				return optionalUser.get().getName();
			}
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
