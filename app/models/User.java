package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
	@Enumerated(EnumType.STRING)
	private Role role = Role.CUSTOMER;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private ApiToken token;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<RequestLog> requests = new ArrayList<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Payment> payments = new ArrayList<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Activation> activations = new ArrayList<>();

	public Long getId() {
		return id;
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

	public ApiToken getToken() {
		return token;
	}

	public void setToken(ApiToken token) {
		this.token = token;
	}

	public List<RequestLog> getRequests() {
		return requests;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public List<Activation> getActivations() {
		return activations;
	}

	public boolean isAdmin() {
		return Role.ADMIN.equals(role);
	}

	public BigDecimal getTotalCredits() {
		BigDecimal totalPayments = payments.stream().map(p -> p.getValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal totalActivations = activations.stream().map(a -> a.getPricePaid())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return totalPayments.subtract(totalActivations);
	}
}
