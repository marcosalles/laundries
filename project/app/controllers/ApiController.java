package controllers;

import actions.RequestLogAction;
import authenticators.AuthenticatedApiRequest;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;

import java.util.HashMap;
import java.util.Map;

@Security.Authenticated(AuthenticatedApiRequest.class)
@With(RequestLogAction.class)
public class ApiController extends Controller {

	public Result teste() {
		Map<String, Object> response = new HashMap<>();
		response.put("authorized", true);
		return ok(Json.toJson(response));
	}
}
