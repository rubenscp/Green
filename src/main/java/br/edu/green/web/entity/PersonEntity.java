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
 * @since 28/08/2016
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

	@Column(name = "user_name")
	@NotNull
	@Size(max = 30)
	private String userName;

	@Column(name = "email")
	@NotNull
	@Size(max = 100)
	private String email;

	@Column(name = "name")
	@NotNull
	@Size(max = 128)
	private String name;

	@Column(name = "password")
	@NotNull
	@Size(max = 64)
	private String password;

	@Column(name = "workplace_name")
	@Size(max = 300)
	private String workplaceName;

	@Column(name = "workplace_initials")
	@Size(max = 32)
	private String workplaceInitials;

	@Column(name = "language")
	@Size(max = 10)
	private String language;

	@Column(name = "theme")
	@Size(max = 30)
	private String theme;

	@Column(name = "green_person_profile_initials")
	@Size(max = 5)
	private String greenPersonProfileInitials;

	@Column(name = "last_update_date")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdateDate;

	public PersonEntity() {
		super();

		// initializing entity class attributes
		this.id = new Long(0);
		this.userName = "";
		this.email = "";
		this.name = "";
		this.password = "";
		this.workplaceName = "";
		this.workplaceInitials = "";
		this.language = "pt";
		this.theme = "";
		this.greenPersonProfileInitials = "USR";
		this.lastUpdateDate = new Date();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the workplaceName
	 */
	public String getWorkplaceName() {
		return workplaceName;
	}

	/**
	 * @param workplaceName
	 *            the workplaceName to set
	 */
	public void setWorkplaceName(String workplaceName) {
		this.workplaceName = workplaceName;
	}

	/**
	 * @return the workplaceInitials
	 */
	public String getWorkplaceInitials() {
		return workplaceInitials;
	}

	/**
	 * @param workplaceInitials
	 *            the workplaceInitials to set
	 */
	public void setWorkplaceInitials(String workplaceInitials) {
		this.workplaceInitials = workplaceInitials;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @param theme
	 *            the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * @return the greenPersonProfileInitials
	 */
	public String getGreenPersonProfileInitials() {
		return greenPersonProfileInitials;
	}

	/**
	 * @param greenPersonProfileInitials
	 *            the greenPersonProfileInitials to set
	 */
	public void setGreenPersonProfileInitials(String greenPersonProfileInitials) {
		this.greenPersonProfileInitials = greenPersonProfileInitials;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate
	 *            the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public String toString() {
		return "PersonEntity: { " + " id: " + this.id + ", userName: " + this.userName + ", email: " + this.email + ", name: " + this.name + ", password: " + this.password + ", workplaceName: " + this.workplaceName + ", workplaceInitials: " + this.workplaceInitials + ", language: " + this.language + ", theme: " + this.theme + ", greenPersonProfileInitials: " + this.greenPersonProfileInitials + ", lastUpdateDate: " + this.lastUpdateDate + " }";
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
