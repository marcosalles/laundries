package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;

import akka.util.Crypt;

@Entity
public class SignupToken extends Model {

	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private User user;
	private String code;

	public SignupToken(User user) {
		this.user = user;
		this.code = Crypt.sha1(user.getName() + user.getEmail() + Crypt.generateSecureCookie());
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getCode() {
		return code;
	}
}
