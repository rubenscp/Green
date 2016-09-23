package br.edu.green.web.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.google.gson.Gson;

/**
 * <p>
 * <b> This class defines the Experiment entity which contains all data about one experiment.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 23/09/2016 (creation date)
 */

@Entity
@Table(name = "experiment")
public class ExperimentEntity implements Serializable {

	private static final long serialVersionUID = -7423754559233895498L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_person_id")
	private PersonEntity ownerPerson;

	@Column(name = "title")
	@Size(max = 128)
	private String title;

	@Column(name = "short_title")
	@Size(max = 20)
	@Null
	private String shortTitle;

	@Column(name = "creation_date")
	@Temporal(TemporalType.DATE)
	@Null
	private Date creationDate;

	@Transient
	private boolean editAction;

	@Transient
	private boolean deleteAction;

	@Transient
	private boolean viewAction;

	/**
	 * 
	 */
	public ExperimentEntity() {
		this(new Long(0), new PersonEntity(), null, null, new Date());
	}

	/**
	 * @param id
	 * @param ownerPerson
	 * @param title
	 * @param shortTitle
	 * @param creationDate
	 * @param editAction
	 * @param deleteAction
	 * @param viewAction
	 */
	public ExperimentEntity(Long id, PersonEntity ownerPerson, String title, String shortTitle, Date creationDate) {
		super();
		this.id = id;
		this.ownerPerson = ownerPerson;
		this.title = title;
		this.shortTitle = shortTitle;
		this.creationDate = creationDate;
	}

	/**
	 * Returns the identification of experiment.
	 * 
	 * @return Long - Experiment identification.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the identification of the experiment.
	 * 
	 * @param id
	 *            The identification of the experiment.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the title of the experiment.
	 * 
	 * @return String - Title of the experiment.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the experiment.
	 * 
	 * @param title
	 *            The title of the experiment.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the ownerPerson
	 */
	public PersonEntity getOwnerPerson() {
		return ownerPerson;
	}

	/**
	 * @param ownerPerson
	 *            the ownerPerson to set
	 */
	public void setOwnerPerson(PersonEntity ownerPerson) {
		this.ownerPerson = ownerPerson;
	}

	/**
	 * @return the shortTitle
	 */
	public String getShortTitle() {
		return shortTitle;
	}

	/**
	 * @param shortTitle
	 *            the shortTitle to set
	 */
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Returns the indicator of that the experiment can be edited by the person.
	 * 
	 * @return boolean - The indicator of that the experiment can be edited by the person.
	 */
	public boolean isEditAction() {
		return editAction;
	}

	/**
	 * Sets the indicator of that the experiment can be edited by the person.
	 * 
	 * @param editAction
	 *            The indicator of that the experiment can be edited by the person.
	 */
	public void setEditAction(boolean editAction) {
		this.editAction = editAction;
	}

	/**
	 * Returns the indicator of that the experiment can be deleted by the person.
	 * 
	 * @return boolean - The indicator of that the experiment can be deleted by the person.
	 */
	public boolean isDeleteAction() {
		return deleteAction;
	}

	/**
	 * Sets the indicator of that the experiment can be deleted by the person.
	 * 
	 * @param deleteAction
	 *            The indicator of that the experiment can be deleted by the person.
	 */
	public void setDeleteAction(boolean deleteAction) {
		this.deleteAction = deleteAction;
	}

	/**
	 * Returns the indicator of that the experiment can be seen by the person.
	 * 
	 * @return boolean - The indicator of that the experiment can be seen by the person.
	 */
	public boolean isViewAction() {
		return viewAction;
	}

	/**
	 * Sets the indicator of that the experiment can be seen by the person.
	 * 
	 * @param deleteAction
	 *            The indicator of that the experiment can be seen by the person.
	 */
	public void setViewAction(boolean viewAction) {
		this.viewAction = viewAction;
	}

	/**
	 * Returns all attributes in the JSON format.
	 * 
	 * @return String - All attributes in the JSON format.
	 */
	@Override
	public String toString() {
		// obtaining all attributes of the class in the JSON format and returning
		return this.getClass().getSimpleName() + ": { " + this.toJSON() + " }";
	}

	/**
	 * Returns a string with all attributes of this class in the JSON format.
	 * 
	 * @return String - The string with all attributes of this class in the JSON format.
	 */
	public String toJSON() {
		try {
			// formatting the class attributes in the JSON format
			Gson gson = new Gson();
			return gson.toJson(this);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns the object hash code.
	 * 
	 * @return int - the object hash code.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * This method implements the equals operator used at the comparison between objects.
	 * 
	 * @param object
	 *            The object to compare with the current object.
	 * @return boolean - the boolean result of the comparison.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ExperimentEntity))
			return false;
		ExperimentEntity other = (ExperimentEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
