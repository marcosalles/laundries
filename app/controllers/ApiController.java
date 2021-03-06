package controllers;

import actions.RequestLogAction;
import authenticators.AuthenticatedApiRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import daos.MachineDAO;
import daos.UserDAO;
import models.Activation;
import models.Machine;
import models.Type;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.F.Tuple;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;

import java.util.*;

public class ApiController extends Controller {

	@Inject
	private FormFactory forms;
	@Inject
	private MachineDAO machines;
	@Inject
	private UserDAO users;

	@Security.Authenticated(AuthenticatedApiRequest.class)
	@With(RequestLogAction.class)
	public Result activate() {
		DynamicForm form = forms.form().bindFromRequest();
		String qrcode = form.field("qrcode").value();
		User user = users.withToken(qrcode).get();
		String id = form.field("id").value();
		Optional<Machine> optionalMachine = machines.withId(id);
		List<String> reasons = new ArrayList<>();
		if (optionalMachine.isPresent()) {
			Machine machine = optionalMachine.get();
			if (userHasEnoughCreditsToUseMachine(user, machine)) {
				if (machine.activate()) {
					machine.update();
					new Activation(user, machine).save();
					return ok(wrap(tuple("authorized", "true")));
				} else {
					reasons.add("Machine already active");
				}
			} else {
				reasons.add("Not enough credit");
			}
		} else {
			reasons.add("Machine id is invalid");
		}
		return badRequest(wrap(tuple("authorized", false), tuple("errors", reasons)));
	}

	private boolean userHasEnoughCreditsToUseMachine(User user, Machine machine) {
		return user.getTotalCredits().compareTo(machine.getUsePrice()) >= 0;
	}

	public Result createMachine(Integer type) {
		Machine machine = new Machine(Type.typeOf(type));
		machine.save();
		return ok(wrap(tuple("id", machine.getId())));
	}

	public Tuple<String, Object> tuple(String key, Object value) {
		return new Tuple<>(key, value);
	}

	public JsonNode wrap (String key, Object value) {
		return Json.toJson(tuple(key, value));
	}

	public JsonNode wrap(Tuple<String, Object>... tuples) {
		Map<String, Object> wrapper = new HashMap<>();
		for (Tuple<String, Object> tuple : tuples) {
			wrapper.put(tuple._1, tuple._2);
		}
		return Json.toJson(wrapper);
	}
}
