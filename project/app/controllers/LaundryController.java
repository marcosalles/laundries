package controllers;

import autenticadores.AuthenticatedAdmin;
import models.Laundry;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import validators.LaundryValidator;
import views.html.newLaundryForm;

import javax.inject.Inject;

@Authenticated(AuthenticatedAdmin.class)
public class LaundryController extends Controller {

	@Inject
	private FormFactory forms;
	@Inject
	private LaundryValidator laundryValidator;

	public Result newLaundryForm() {
		Laundry laundry = new Laundry();
		Form<Laundry> formulario = forms.form(Laundry.class).fill(laundry);
		return ok(newLaundryForm.render(formulario));
	}

	public Result saveLaundry() {
		Form<Laundry> form = forms.form(Laundry.class).bindFromRequest();
		Laundry laundry = form.get();
		if (laundryValidator.hasErrors(form)) {
			flash("danger", "There are errors in your form!");
			return badRequest(newLaundryForm.render(form));
		}
		laundry.save();
		flash("success", "Your laundry '" + "' has been created!");
		return redirect(routes.LaundryController.newLaundryForm());
	}

}
