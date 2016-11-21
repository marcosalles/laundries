package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class RequestLog extends Model {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private User user;
	private String ip;
	private String uri;
	private Date date;

	public RequestLog(User user, String ip, String uri) {
		this.user = user;
		this.ip = ip;
		this.uri = uri;
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getIp() {
		return ip;
	}

	public String getUri() {
		return uri;
	}

	public Date getDate() {
		return date;
	}

}
