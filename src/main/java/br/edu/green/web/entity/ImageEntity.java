package br.edu.green.web.entity;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.Gson;

/**
 * <p>
 * <b> This class defines the Image entity used in one experiment.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 09/11/2016 (creation date)
 */

@Entity
@Table(name = "image")
public class ImageEntity implements Serializable {

	private static final long serialVersionUID = -5256294703085348308L;

	@Id
	@SequenceGenerator(name = "pk_sequence_image", sequenceName = "image_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_sequence_image")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "experiment_id")
	@NotNull
	private ExperimentEntity experiment;

	@Column(name = "external_name")
	@Size(max = 256)
	private String externalName;

	@Column(name = "internal_name")
	@Size(max = 256)
	private String internalName;

	@Column(name = "internal_path")
	@Size(max = 512)
	private String internalPath;

	@Column(name = "acquire_date")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date acquireDate;

	@Column(name = "size")
	private Long size;

	@Column(name = "width")
	private Integer width;

	@Column(name = "height")
	private Integer height;

	@Column(name = "altitude")
	private Double altitude;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "import_date")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date importDate;

	@Column(name = "file_format")
	@Size(max = 20)
	private String fileFormat;

	@Transient
	private boolean deleteAction;

	@Transient
	private boolean viewAction;

	@Transient
	BufferedImage image;

	/**
	 * Default constructor
	 */
	public ImageEntity() {
		this(new Long(0), new ExperimentEntity(), "", "", "", new Date(), new Long(0), new Integer(0), new Integer(0), new Double(0), new Double(0), new Double(0), new Date(), null);
	}

	/**
	 * @param id
	 * @param experiment
	 * @param externalName
	 * @param internalName
	 * @param internalPath
	 * @param acquireDate
	 * @param size
	 * @param width
	 * @param height
	 * @param altitude
	 * @param latitude
	 * @param longitude
	 * @param importDate
	 */
	public ImageEntity(Long id, ExperimentEntity experiment, String externalName, String internalName, String internalPath, Date acquireDate, Long size,
			Integer width, Integer height, Double altitude, Double latitude, Double longitude, Date importDate, BufferedImage image) {
		super();
		this.id = id;
		this.experiment = experiment;
		this.externalName = externalName;
		this.internalName = internalName;
		this.internalPath = internalPath;
		this.acquireDate = acquireDate;
		this.size = size;
		this.width = width;
		this.height = height;
		this.altitude = altitude;
		this.latitude = latitude;
		this.longitude = longitude;
		this.importDate = importDate;
		this.image = image;
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
	 * @return the experiment
	 */
	public ExperimentEntity getExperiment() {
		return experiment;
	}

	/**
	 * @param experiment
	 *            the experiment to set
	 */
	public void setExperiment(ExperimentEntity experiment) {
		this.experiment = experiment;
	}

	/**
	 * @return the externalName
	 */
	public String getExternalName() {
		return externalName;
	}

	/**
	 * @param externalName
	 *            the externalName to set
	 */
	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}

	/**
	 * @return the internalName
	 */
	public String getInternalName() {
		return internalName;
	}

	/**
	 * @param internalName
	 *            the internalName to set
	 */
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	/**
	 * @return the internalPath
	 */
	public String getInternalPath() {
		return internalPath;
	}

	/**
	 * @param internalPath
	 *            the internalPath to set
	 */
	public void setInternalPath(String internalPath) {
		this.internalPath = internalPath;
	}

	/**
	 * @return the acquireDate
	 */
	public Date getAcquireDate() {
		return acquireDate;
	}

	/**
	 * @param acquireDate
	 *            the acquireDate to set
	 */
	public void setAcquireDate(Date acquireDate) {
		this.acquireDate = acquireDate;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return the altitude
	 */
	public Double getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude
	 *            the altitude to set
	 */
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the importDate
	 */
	public Date getImportDate() {
		return importDate;
	}

	/**
	 * @param importDate
	 *            the importDate to set
	 */
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	/**
	 * Returns the image file format.
	 * 
	 * @return String - The image file format.
	 */
	public String getFileFormat() {
		return fileFormat;
	}

	/**
	 * Returns the image file format.
	 * 
	 * @param fileFormat
	 * 
	 * @return String - The image file format.
	 */
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
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
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
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
		if (!(obj instanceof ImageEntity))
			return false;
		ImageEntity other = (ImageEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
