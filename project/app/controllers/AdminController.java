package controllers;

import autenticadores.AuthenticatedAdmin;
import daos.LaundryDAO;
import daos.UserDAO;
import models.Laundry;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.listLaundries;
import views.html.listUsers;

import javax.inject.Inject;
import java.util.List;

@Authenticated(AuthenticatedAdmin.class)
public class AdminController extends Controller {

	@Inject
	private LaundryDAO laundries;
	@Inject
	private UserDAO users;

	public Result users() {
		List<User> users = this.users.all();
		return ok(listUsers.render(users));
	}

	public Result laundries() {
		List<Laundry> laundries = this.laundries.all();
		return ok(listLaundries.render(laundries));
	}
}
