package models;

import akka.util.Crypt;
import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Optional;

@Entity
public class ApiToken extends Model {

	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private final User user;
	private final String code;

	public ApiToken(User user) {
		this.user = user;
		this.code = Crypt.sha1(Crypt.generateSecureCookie()+user.toString());
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return Optional.ofNullable(getCode()).orElse("");
	}
}
