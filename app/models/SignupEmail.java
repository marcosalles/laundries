package models;

public class SignupEmail extends play.libs.mailer.Email {
	private static final String SENDER = "Laundry <no-reply@laundry.com>";
	private static final String SUBJECT = "Verify the email for your account on our laundry manager!";
	private static final String BODY_FORMAT = "Hello, %s! Please click the following link to verify your account! <a href='%s'>Verify email!</a>";

	public SignupEmail(SignupToken token) {
		User user = token.getUser();
		this.setFrom(SENDER);
		this.addTo(recipient(user));
		this.setSubject(SUBJECT);
		this.setBodyHtml(buildBody(user, token));
	}

	private String recipient(User user) {
		return String.format("%s <%s>", user.getName(), user.getEmail());
	}

	private String buildLink(String email, String code) {
		return String.format("https://laundries.herokuapp.com/user/verify/%s/%s", email, code);
	}

	private String buildBody(User user, SignupToken token) {
		String link = buildLink(user.getEmail(), token.getCode());
		return String.format(BODY_FORMAT, user.getName(), link);
	}
}
