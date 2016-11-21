package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Model {

	@Id
	@GeneratedValue
	private Long id;
	@Required(message = "Required field")
	private String name;
	@Required(message = "Required field")
	private String email;
	@Required(message = "Required field")
	private String password;
	private boolean verified = false;
	private Role role = Role.CUSTOMER;
	@OneToOne(mappedBy = "user")
	private ApiToken token;
	@OneToMany(mappedBy = "user")
	private List<RequestLog> requests;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ApiToken getToken() {
		return token;
	}

	public void setToken(ApiToken token) {
		this.token = token;
	}

	public List<RequestLog> getRequests() {
		return requests;
	}

	public void setRequests(List<RequestLog> requests) {
		this.requests = requests;
	}

	public boolean isAdmin() {
		return Role.ADMIN.equals(role);
	}
}
