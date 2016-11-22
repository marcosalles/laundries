package controllers;

import authenticators.AuthenticatedAdmin;
import daos.LaundryDAO;
import daos.UserDAO;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import validators.LaundryValidator;
import validators.UserValidator;
import views.html.*;

import javax.inject.Inject;
import java.util.Optional;

@Authenticated(AuthenticatedAdmin.class)
public class AdminController extends Controller {


	@Inject
	private FormFactory forms;
	@Inject
	private UserValidator userValidator;
	@Inject
	private UserDAO users;
	@Inject
	private LaundryValidator laundryValidator;
	@Inject
	private LaundryDAO laundries;
//	@Inject
//	private MachineValidator machineValidator;
//	@Inject
//	private MachineDAO machines;

	public Result users() {
		return ok(listUsers.render(users.all()));
	}

	public Result userForm() {
		Form<User> form = forms.form(User.class);
		return ok(adminUserForm.render(form));
	}

	public Result userCreate() {
		Form<User> form = forms.form(User.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(adminUserForm.render(form));
		}
		User user = form.get();
		user.save();
		new ApiToken(user).save();
		Double payment = Double.parseDouble(form.field("payment").valueOr("0.00"));
		new Payment(user, payment).save();
		user.setVerified(true);
		user.update();
		return redirect(controllers.routes.AdminController.users());
	}

	public Result userEdit(Long id) {
		return TODO;// TODO
	}

	public Result userUpdate() {
		return TODO;// TODO
	}

	public Result userDelete(Long id) {
		users.withId(id).ifPresent(User::delete);
		return redirect(controllers.routes.AdminController.users());
	}

	public Result userAddCredits(Long id, Double value) {
		Optional<User> optionalUser = users.withId(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			new Payment(user, value).save();
		}
		return redirect(controllers.routes.AdminController.users());
	}

	public Result laundries() {
		return ok(listLaundries.render(laundries.all()));
	}

	public Result laundryForm() {
		Form<Laundry> form = forms.form(Laundry.class);
		return ok(adminLaundryForm.render(form));
	}

	public Result laundryCreate() {
		Form<Laundry> form = forms.form(Laundry.class).bindFromRequest();
		Laundry laundry = form.get();
		if (laundryValidator.hasErrors(form)) {
			flash("danger", "There are errors in your form!");
			return badRequest(adminLaundryForm.render(form));
		}
		laundry.save();
		flash("success", "Your laundry '" + laundry.getName() + "' has been created!");
		return redirect(controllers.routes.AdminController.laundries());
	}

	public Result laundryEdit(Long id) {
		return TODO;// TODO
	}

	public Result laundryUpdate() {
		return TODO;// TODO
	}

	public Result laundryDelete(Long id) {
		laundries.withId(id).ifPresent(Laundry::delete);
		return redirect(controllers.routes.AdminController.laundries());
	}

	public Result machines(Long id) {
		Laundry laundry = laundries.withId(id).get();
		return ok(listMachines.render(laundry));
	}

	public Result machineForm() {
		Form<Machine> form = forms.form(Machine.class);
		return ok(adminMachineForm.render(form, laundries.all()));
	}

	public Result machineCreate() {
		Form<Machine> form = forms.form(Machine.class).bindFromRequest();
		Optional<Laundry> optionalLaundry = laundryFromForm(form);
		Machine machine = form.get();
		optionalLaundry.ifPresent(laundry -> {
			machine.setLaundry(laundry);
			machine.save();
		});
		Long id = machine.getLaundry().getId();
		return redirect(controllers.routes.AdminController.machines(id));
	}

	private Optional<Laundry> laundryFromForm(Form<Machine> form) {
		Long id = Long.parseLong(form.field("laundry_id").value());
		return laundries.withId(id);
	}

	public Result machineEdit(String id) {
		return TODO;// TODO
	}

	public Result machineUpdate() {
		return TODO;// TODO
	}

	public Result machineDelete(String id) {
		return TODO;// TODO
	}

}
