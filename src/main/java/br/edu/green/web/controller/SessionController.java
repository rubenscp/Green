package br.edu.green.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.service.PersonService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.Util;

/**
 * SessionController.java: This class defines attributes that will maintained
 * during a current session of the logged person.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 25/08/2015
 * 
 */
@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController implements Serializable {

	private static final long serialVersionUID = 4742262988364358523L;

	// defining class attributes
	private PersonEntity personEntity;
	private String currentLanguage;

	@EJB
	PersonService personService;

	LanguageController languageController; 
	/**
	 * Construtor method
	 * 
	 * @param
	 */
	
	@PostConstruct
	private void init() {
		// initializing entity class attributes
		this.personEntity = new PersonEntity();

		// restoring objects of the session context
		HttpSession httpSession = FacesUtil.getSession();
		this.personEntity = (PersonEntity) httpSession.getAttribute(Util.className(PersonEntity.class.getName()));
		
		languageController = new LanguageController();
	}

	/**
	 * Get method
	 * 
	 * @param none
	 * @return PersonEntity
	 */
	public PersonEntity getPersonEntity() {
		return personEntity;
	}

	/**
	 * Set method
	 * 
	 * @param VirtualPersonSIEXPEntity
	 * @return void
	 */
	public void setPersonEntity(PersonEntity personEntity) {
		this.personEntity = personEntity;
	}

	/**
	 * Obtains the first name of the logged person
	 * 
	 * @param none
	 * @return String
	 */
	public String getCompactNamePerson() {
		return Util.compactNamePerson(personEntity.getName());
	}

	/**
	 * Updates person entity with changed language and theme
	 * 
	 * @param none
	 * @return Result
	 */
	public String updateLoggedPerson() {
		// updates person entity
		if (this.personService.save(this.personEntity) == null) {
			return "error.xhtml";
		} else {
			return "main.xhtml";
		}
	}

	/**
	 * Get method
	 * 
	 * @param none
	 * @return currentLanguage
	 */
	public String getCurrentLanguage() {
		return currentLanguage;
	}

	/**
	 * Set method
	 * 
	 * @param currentLanguage
	 * @return none
	 */
	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}
	
	/**
	 * Remove person object of the session objects list
	 * 
	 * @param none 
	 * @return none
	 */	
	public void startNewLogin() {
		// restoring objects of the session context
		HttpSession httpSession = FacesUtil.getSession();
		httpSession.removeAttribute(Util.className(PersonEntity.class.getName()));
		
		languageController.setLanguage(LanguageController.DEFAULT_LANGUAGE);
	}
	

}
