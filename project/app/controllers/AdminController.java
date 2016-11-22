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
	private LaundryValidator laundryValidator;
	@Inject
	private LaundryDAO laundries;
	@Inject
	private UserValidator userValidator;
	@Inject
	private UserDAO users;

	public Result laundries() {
		return ok(listLaundries.render(laundries.all()));
	}

	public Result laundryForm() {
		Laundry laundry = new Laundry();
		Form<Laundry> form = forms.form(Laundry.class).fill(laundry);
		return ok(newLaundryForm.render(form));
	}

	public Result laundryCreate() {
		Form<Laundry> form = forms.form(Laundry.class).bindFromRequest();
		Laundry laundry = form.get();
		if (laundryValidator.hasErrors(form)) {
			flash("danger", "There are errors in your form!");
			return badRequest(newLaundryForm.render(form));
		}
		laundry.save();
		flash("success", "Your laundry '" + laundry.getName() + "' has been created!");
		return redirect(controllers.routes.AdminController.laundries());
	}

	public Result laundryEdit() {
		return TODO;// TODO
	}

	public Result laundryUpdate() {
		return TODO;// TODO
	}

	public Result laundryDelete() {
		return TODO;// TODO
	}

	public Result users() {
		return ok(listUsers.render(users.all()));
	}

	public Result userForm() {
		Form<User> form = forms.form(User.class);
		return ok(adminUserForm.render(form));
	}

	public Result userCreate() {
		Form<User> form = forms.form(User.class).bindFromRequest();
		if(form.hasErrors()) {
			return badRequest(adminUserForm.render(form));
		}
		User user = form.get();
		user.save();
		new ApiToken(user).save();
		String payment = form.field("payment").valueOr("0.00");
		new Payment(user, payment).save();
		user.setVerified(true);
		user.update();
		return redirect(controllers.routes.AdminController.users());
	}

	public Result userEdit() {
		return redirect(controllers.routes.AdminController.users());// TODO
	}

	public Result userUpdate() {
		return redirect(controllers.routes.AdminController.users());// TODO
	}

	public Result userDelete(String code) {
		User user = users.withToken(code).get();
		user.getToken().delete();
		user.setVerified(false);
		user.update();
		return redirect(controllers.routes.AdminController.users());
	}

	public Result machineForm() {
		Form<Machine> form = forms.form(Machine.class);
		return TODO;// TODO
	}

	public Result machineCreate() {
		return redirect(controllers.routes.AdminController.users());// TODO
	}

	public Result machineEdit() {
		return redirect(controllers.routes.AdminController.users());// TODO
	}

	public Result machineUpdate() {
		return redirect(controllers.routes.AdminController.users());// TODO
	}

	public Result machineDelete() {
		return redirect(controllers.routes.AdminController.users());// TODO
	}

}
