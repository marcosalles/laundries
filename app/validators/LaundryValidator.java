package validators;

import daos.LaundryDAO;
import models.Laundry;
import play.data.Form;

import javax.inject.Inject;

public class LaundryValidator {

	@Inject
	private LaundryDAO laundryDAO;

	public boolean hasErrors(Form<Laundry> form) {
		return form.hasErrors();
	}
}
