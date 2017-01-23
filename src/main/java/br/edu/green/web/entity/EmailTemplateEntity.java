package br.edu.green.web.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.Gson;

/**
 * <p>
 * <b>This class defines the Email Template entity used to define the main properties of an email sent by SITIS.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 17/01/2017 (creation date)
 */

@Entity
@Table(name = "email_template")
public class EmailTemplateEntity implements Serializable {

	private static final long serialVersionUID = 872365753516355877L;

	@Id
	@Column(name = "identifier", unique = true, nullable = false, length = 100)
	@Size(max = 100)
	@NotNull
	private String identifier;

	@Column(name = "subject", length = 512)
	@Size(max = 512)
	@NotNull
	private String subject;

	@Column(name = "body", length = 32768)
	@Size(max = 32768)
	private String body;

	@Column(name = "footer", length = 512)
	@Size(max = 512)
	@NotNull
	private String footer;

	@Transient
	List<String> toEmails;

	@Transient
	private boolean editAction;

	@Transient
	private boolean deleteAction;

	@Transient
	private boolean viewAction;

	/**
	 * Default class constructor.
	 */
	public EmailTemplateEntity() {
		super();

		// initializing entity class attributes
		this.identifier = "";
		this.subject = "";
		this.body = "";
		this.footer = "";
		this.toEmails = new ArrayList<String>(0);
		this.editAction = false;
		this.deleteAction = false;
		this.viewAction = false;
	}

	/**
	 * Returning the identifier of the email template.
	 * 
	 * @return String - the identifier of the email template.
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Setting the identifier of the email template.
	 * 
	 * @param identifier
	 *            The identifier of the email template.
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Returning the subject of the email template.
	 * 
	 * @return String - the subject of the email template.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Setting the subject of the email template.
	 * 
	 * @param subject
	 *            The subject of the email template.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Returning the body of the email template.
	 * 
	 * @return String - the body of the email template.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Setting the body of the email template.
	 * 
	 * @param body
	 *            The body of the email template.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Returning the footer of the email template.
	 * 
	 * @return String - the footer of the email template.
	 */
	public String getFooter() {
		return footer;
	}

	/**
	 * Setting the footer of the email template.
	 * 
	 * @param footer
	 *            The footer of the email template.
	 */
	public void setFooter(String footer) {
		this.footer = footer;
	}

	/**
	 * Returning the list of destinations e-mails.
	 * 
	 * @return List<String> - the list of destinations e-mails.
	 */
	public List<String> getToEmails() {
		return toEmails;
	}

	/**
	 * Setting the flist of destinations e-mails.
	 * 
	 * @param toEmails
	 *            The list of destinations e-mails.
	 */
	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}

	/**
	 * @return the editAction
	 */
	public boolean isEditAction() {
		return editAction;
	}

	/**
	 * @param editAction
	 *            the editAction to set
	 */
	public void setEditAction(boolean editAction) {
		this.editAction = editAction;
	}

	/**
	 * @return the deleteAction
	 */
	public boolean isDeleteAction() {
		return deleteAction;
	}

	/**
	 * @param deleteAction
	 *            the deleteAction to set
	 */
	public void setDeleteAction(boolean deleteAction) {
		this.deleteAction = deleteAction;
	}

	/**
	 * @return the viewAction
	 */
	public boolean isViewAction() {
		return viewAction;
	}

	/**
	 * @param viewAction
	 *            the viewAction to set
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
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	/**
	 * This method implements the equals operator used at the comparison between objects.
	 * 
	 * @param object
	 *            The object to compare with the current object.
	 * @return boolean - The boolean result of the comparison.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EmailTemplateEntity))
			return false;
		EmailTemplateEntity other = (EmailTemplateEntity) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
}
