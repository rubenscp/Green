package br.edu.green.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.service.PersonService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.MessagesGreen;
import br.edu.green.web.util.Util;
import br.edu.green.web.validate.Result;

/**
 * LoggedPersonController.java: This class controls the logged person attributes.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 08/09/2015
 * 
 */
@ManagedBean(name = "loggedPersonController")
@SessionScoped
public class LoggedPersonController implements Serializable {

	private static final long serialVersionUID = 1109526142575855374L;

	// defining class attributes
	private PersonEntity personEntity;
	private MessagesGreen messagesSITIS;
	private Result results;

	@EJB
	PersonService personService;

	/**
	 * Construtor method
	 * 
	 * @param
	 */
	@PostConstruct
	private void init() {

		// initializing entity class attributes
		this.personEntity = new PersonEntity();
		this.messagesSITIS = MessagesGreen.getInstanceof();
		this.results = Result.getInstanceof();

		// restoring objects of the session context
		HttpSession httpSession = FacesUtil.getSession();
		this.personEntity = (PersonEntity) httpSession.getAttribute(Util.className(PersonEntity.class.getName()));

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
	 * Updates person entity with changed language and theme
	 * 
	 * @param none
	 * @return Result
	 */
	public void save() {
		
		// cleaning results 
		this.results.clear();
		
		// updates person entity
		if (this.personService.save(this.personEntity) != null) {
			// creating of the success message
			this.results.addMessages(this.messagesSITIS.getMensagem("warning.logged.person.001"), Result.SUCCESS);
		} else {
			// creating of the error message
			this.results.addMessages(this.messagesSITIS.getMensagem("error.logged.person.001"), Result.ERROR);
		}
		
		// adding messages to faces messages context
		FacesContext facesContext = FacesUtil.getContext();		
		results.addMessagesToFacesMessages(facesContext);	
	}

	/**
	 * Returns to main page
	 * 
	 * @param none
	 * @return main page name
	 */
	public String returnMainPage() {
		return "main.xhtml";
	}
	
	public String getLastUpdateDateLoggedPerson() {
		return Util.formatterDate(this.personEntity.getLastUpdateDate());
	}
}
