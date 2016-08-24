package br.edu.green.web.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class defines the Person entity which contains all attributes about the system user.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015
 * 
 */

@Entity
@Table(name = "person")
public class PersonEntity implements Serializable {

	private static final long serialVersionUID = -6513294307557663963L;

	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "person_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "login")
	@NotNull
	@Size(max = 32)
	private String login;

	@Column(name = "name")
	@NotNull
	@Size(max = 128)
	private String name;

	@Column(name = "password")
	@NotNull
	@Size(max = 20)
	private String password;

	@Column(name = "workplace_name")
	@Size(max = 300)
	private String workplaceName;

	@Column(name = "workplace_initials")
	@Size(max = 32)
	private String workplaceInitials;

	@Column(name = "email")
	@Size(max = 500)
	private String email;

	@Column(name = "last_update_date")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdateDate;

	@Column(name = "language")
	@Size(max = 10)
	private String language;

	@Column(name = "theme")
	@Size(max = 30)
	private String theme;

	@Column(name = "green_person_profile_initials")
	@Size(max = 5)
	private String greenPersonProfileInitials;

	public PersonEntity() {
		super();

		// initializing entity class attributes
		this.id = new Long(0);
		this.login = "";
		this.name = "";
		this.password = "";
		this.workplaceName = "";
		this.workplaceInitials = "";
		this.email = "";
		this.lastUpdateDate = new Date();
		this.language = "pt";
		this.theme = "";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWorkplaceName() {
		return workplaceName;
	}

	public void setWorkplaceName(String workplaceName) {
		this.workplaceName = workplaceName;
	}

	public String getWorkplaceInitials() {
		return workplaceInitials;
	}

	public void setWorkplaceInitials(String workplaceInitials) {
		this.workplaceInitials = workplaceInitials;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {
		return "PersonEntity: { " + " id: " + this.id + ", login: " + this.login + ", name: " + this.name + ", password: " + this.password + ", workplaceName: " + this.workplaceName + ", workplaceInitials: " + this.workplaceInitials + ", email: " + this.email + ", lastUpdateDate: " + this.lastUpdateDate + ", language: " + this.language + ", theme: " + this.theme + " }";
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof PersonEntity)) {
			return false;
		}
		PersonEntity other = (PersonEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

}
