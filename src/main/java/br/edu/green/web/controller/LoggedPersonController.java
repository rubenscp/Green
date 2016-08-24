package br.edu.green.web.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.entity.enumerate.GreenPersonProfileEnum;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.service.PersonService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.Util;

/**
 * LoggedPersonController.java: This class controls the logged person attributes.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 08/09/2015
 * 
 */

/**
 * <p>
 * <b> This class controls the attributes of the logged person in the SITIS.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 19/08/2015 (creation date)
 */

@ManagedBean(name = "loggedPersonController")
@ViewScoped
public class LoggedPersonController extends GeneralController implements Serializable {

	// defining serial version
	private static final long serialVersionUID = 7373279452118114144L;

	// defining class attributes
	private PersonEntity personEntity;

	@EJB
	PersonService personService;

	/**
	 * Default constructor.
	 */
	public LoggedPersonController() {
		super();
	}

	/**
	 * Initializes some class objects and runs after rendering the JSF page.
	 */
	@PostConstruct
	private void init() {

		try {
			// initializing services
			this.initializeServices();

			// initializing entity class attributes
			this.personEntity = new PersonEntity();

			// recoverying the person object in the session of JSF context
			this.personEntity = FacesUtil.recoverObjectInSession(new PersonEntity());

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web.
			this.handleGeneralException(ge);
		}
	}

	/**
	 * Accesses the attribute named 'personEntity' and returns its value.
	 * 
	 * @return personEntity - The logged person in the SITIS.
	 */
	public PersonEntity getPersonEntity() {
		return personEntity;
	}

	/**
	 * Sets the value of attribute named 'personEntity'.
	 * 
	 * @param personEntity
	 *            The logged person in the SITIS.
	 */
	public void setPersonEntity(PersonEntity personEntity) {
		this.personEntity = personEntity;
	}

	/**
	 * Returns the last update date of the logged person in the SITIS.
	 * 
	 * @return String - the last update date of the logged person in the SITIS.
	 */
	public String getLastUpdateDateLoggedPerson() {
		return Util.formatterDate(this.personEntity.getLastUpdateDate(), Util.FORMAT_DDMMAAAA_HHMMSS);
	}

	public String getSitisPersonProfile() {
		// return GreenPersonProfileEnum.getEnum(this.personEntity.getSitisPersonProfileInitials()).toString();
		return "";
	}

	/**
	 * Returns the name of main page (xhtml)
	 * 
	 * @return String - the name of main page (xhtml)
	 */
	public void returnMainPage() {
		try {
			// calls main form
			FacesUtil.getContext().getExternalContext().redirect(this.applicationConfiguration.getStringValue("xhtml.form.name.activities.web.project"));

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web
			this.handleGeneralException(ge);

		} catch (IOException ioe) {
			// handling IOException with creating new general exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_INPUT_OUTPUT, this.applicationMessage.getMessage(Code.FACES_EXCEPTION_INPUT_OUTPUT.name())));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("returnMainPage");
			ge.setExceptionOriginalMessage(ioe.getMessage()); // the original exception message

			// handling general exception of SITIS Web
			this.handleGeneralException(ge);

		} catch (Exception e) {
			// handling exception
			this.handleException(e);
		}
	}

}
