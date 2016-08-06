package br.edu.green.web.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.service.ws.WebServiceCorporativeService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.MessagesGreen;
import br.edu.green.web.util.Util;
import br.edu.green.web.validate.Result;

/**
 * This class defines the services used to authenticates a person in the SITIS
 * Web.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015
 * 
 */

@Stateless
public class PersonAuthenticationServiceBean implements
		PersonAuthenticationService {

	@EJB
	private PersonService personService;

	@EJB
	private WebServiceCorporativeService webServiceCorporativeService;

	// defining attibutes
	private MessagesGreen messagesSITIS;
	private Result results;

	// constructor method
	public PersonAuthenticationServiceBean() {

		// creating work objects
		this.messagesSITIS = MessagesGreen.getInstanceof();
		this.results = Result.getInstanceof();
	}

	/**
	 * This method validates the person data using the Embrapa security system
	 * enabled by DTI. If successful validate, it updates de local data base to
	 * future access.
	 * 
	 * @param login
	 * @param password
	 * @return PersonEntity
	 */
	public Result validate(String login, String password, String language) {

		// clearing results
		this.results.clear();

		// removing spaces of the string
		login = login.trim();
		password = password.trim();

		// validating attributes contents
		if (login.isEmpty()) {
			this.results.addMessages(this.messagesSITIS.getMensagem("error.login.001"), Result.ERROR);
		}
		if (password.isEmpty()) {
			this.results.addMessages(this.messagesSITIS.getMensagem("error.login.002"),	Result.ERROR);
		}

		// if exists error, return
		if (this.results.isError()) {
			return this.results;
		}

		// checking data of the person at local data base
		this.validatePersonAtLocalDataBase(login, password, language);

		// return results
		return this.results;
	}

	private void validatePersonAtLocalDataBase(String corporateAccount, String password, String language) {

		// finding person at the local data base
		PersonEntity personEntity = null;
		personEntity = this.personService.findByLogin(corporateAccount);
		if (personEntity == null) {
			this.results.addMessages(this.messagesSITIS.getMensagem("error.login.004"),	Result.ERROR);
			return;
		}

		// checking password
		//String passwordInformedByPerson = Util.generateMD5(password);
		if (!password.equals(personEntity.getPassword())) {
			this.results.addMessages(this.messagesSITIS.getMensagem("error.login.005"),	Result.ERROR);
			return;
		}

		// updating data person
		personEntity.setLastUpdateDate(new Date());
		if (!language.isEmpty()) {
			personEntity.setLanguage(language);
		}

		// creating or updating person object at the local data base
		this.personService.save(personEntity);

		// storing the logged person object in the session object
		this.storePersonEntity(personEntity);
	}

	@Override
	public boolean disconnectPerson() {

		// removing the logged person object in the attributes of the session
		// object
		HttpSession httpSession = FacesUtil.getSession();
		httpSession.removeAttribute(Util.className(PersonEntity.class.getName()));

		// successful operation
		return true;
	}

	private void storePersonEntity(PersonEntity personEntity) {

		// storing the logged person object in the session object
		HttpSession httpSession = FacesUtil.getSession();
		httpSession.setAttribute(Util.className(PersonEntity.class.getName()), personEntity);
	}

}
