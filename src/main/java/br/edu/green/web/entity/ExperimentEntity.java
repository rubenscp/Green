package br.edu.green.web.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
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
	@SequenceGenerator(name = "pk_sequence_experiment", sequenceName = "experiment_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence_experiment")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "public_identifier")
	@NotNull
	private Long publicIdentifier;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_person_id")
	@NotNull
	private PersonEntity ownerPerson;

	@Column(name = "title")
	@Size(max = 128)
	private String title;

	@Column(name = "description")
	@Size(max = 1000)
	@NotNull
	private String description;

	@Column(name = "creation_date")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date creationDate;

	@Column(name = "last_update_date")
	@Temporal(TemporalType.DATE)
	@Null
	private Date lastUpdateDate;

	// mapping one to many related to ExperimentEntity class
	@OneToMany(mappedBy = "experiment", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ImageEntity> images;

	@Transient
	private boolean editAction;

	@Transient
	private boolean deleteAction;

	@Transient
	private boolean viewAction;

	@Transient
	private boolean imagesUploadAction;

	/**
	 * 
	 */
	public ExperimentEntity() {
		this(new Long(0), new Long(0), new PersonEntity(), null, null, new Date(), null, new ArrayList<ImageEntity>(0));
	}

	/**
	 * @param id
	 * @param publicIdentifier
	 * @param ownerPerson
	 * @param title
	 * @param description
	 * @param creationDate
	 * @param lastUpdateDate
	 */
	public ExperimentEntity(Long id, Long publicIdentifier, PersonEntity ownerPerson, String title, String description, Date creationDate, Date lastUpdateDate,
			List<ImageEntity> images) {
		super();
		this.id = id;
		this.publicIdentifier = publicIdentifier;
		this.ownerPerson = ownerPerson;
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.images = images;
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
	 * Returns the public identifier of experiment.
	 * 
	 * @return the publicIdentifier
	 */
	public Long getPublicIdentifier() {
		return publicIdentifier;
	}

	/**
	 * Sets the public identifier of experiment.
	 * 
	 * @param publicIdentifier
	 *            The public identifier of experiment.
	 */
	public void setPublicIdentifier(Long publicIdentifier) {
		this.publicIdentifier = publicIdentifier;
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
	 * Returns the short title of the experiment.
	 * 
	 * @return String - The short title of the experiment.
	 */
	public String getShortTitle() {
		String shortTitle = "";
		int limit = 50;
		if (this.title.length() > limit) {
			shortTitle = this.title.substring(0, limit);
		} else {
			shortTitle = this.title;
		}
		return shortTitle;
	}

	public String getPublicIdentifierAndShortTitle() {
		String publicIdentifierAndShortTitle = "";
		String tail = "";
		if (this.publicIdentifier != null && this.publicIdentifier > 0 && !this.title.equals("")) {
			int sizeLimit = this.title.length();
			if (sizeLimit > 50) {
				sizeLimit = 50;
				tail = "...";
			}
			publicIdentifierAndShortTitle = this.publicIdentifier + " - " + this.title.substring(0, sizeLimit) + tail;
		}
		return publicIdentifierAndShortTitle;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

	/**
	 * @return the images
	 */
	public List<ImageEntity> getImages() {
		return images;
	}

	/**
	 * @param images
	 *            the images to set
	 */
	public void setImages(List<ImageEntity> images) {
		this.images = images;
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
	 * Returns the indicator of the experiment to do images upload.
	 * 
	 * @return boolean - The indicator of the experiment to do images upload.
	 */
	public boolean isImagesUploadAction() {
		return imagesUploadAction;
	}

	/**
	 * Sets the indicator of the experiment to do images upload.
	 *
	 * @param imagesUploadAction
	 *            The indicator of the experiment to do images upload.
	 */
	public void setImagesUploadAction(boolean imagesUploadAction) {
		this.imagesUploadAction = imagesUploadAction;
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
