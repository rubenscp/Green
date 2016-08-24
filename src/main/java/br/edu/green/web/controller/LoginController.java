package br.edu.green.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.service.PersonAuthenticationService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.Util;

/**
 * <p>
 * <b> This class controls the validation of the person in access SITIS.</b>
 * </p>
 * <p>
 * The first authentication is made into corporative web service and, if it faults, the second authentication is made at the local data base.
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 19/08/2015 (creation date)
 */

@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController extends GeneralController implements Serializable {

	// defining serial version
	private static final long serialVersionUID = 8180609930379821697L;

	// defining constants
	// private static final String MAIN_PAGE = "main";
	// private static final String LOGIN_PAGE = "login";
	// private static final String ACTIVITIES_SITIS_WEB_PROECT_PAGE = "activitiesSitisWebProject";

	// defining the class attributes
	private String login;
	private String password;

	@EJB
	private PersonAuthenticationService personAuthenticationService;

	/**
	 * Default constructor.
	 */
	public LoginController() {
		super();
	}

	/**
	 * Prepares the environment of the controller class.
	 */
	@PostConstruct
	public void loadForm() {
		try {
			// initializing services
			this.initializeServices();

			// removing navigation history menu object
			this.removeNavigationHistoryMenu();

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web
			this.handleGeneralException(ge);
		}
	}

	/**
	 * Validates corporate account and its password using the web services of security control system and, when theses web services are disable, it uses the
	 * local data base.
	 * 
	 * @return String - the page name after of validate with success or not
	 */
	public String validate() {
		try {
			// checking operation status of SITIS Web.
			this.initializeServices();

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web
			this.handleGeneralException(ge);

			// returning to the login page
			return this.applicationConfiguration.getStringValue("xhtml.form.name.login");
		}

		// creating objects
		String currentLanguage = "";
		boolean statusChangedLanguage = false;

		// clearing results
		this.processingResultsList.clear();

		// clearing function title

		// checking changes of the language
		try {
			statusChangedLanguage = (boolean) FacesUtil.getSession().getAttribute(Util.className(SessionController.class.getName()));
		} catch (Exception e) {
			statusChangedLanguage = false;
		}
		if (statusChangedLanguage) {
			try {
				currentLanguage = FacesUtil.getContext().getViewRoot().getLocale().getLanguage();
			} catch (GeneralException e) {
				currentLanguage = "";
			}
		} else {
			currentLanguage = "";
		}

		// checking corporate account and password
		try {
			// running login of the person
			ProcessingResultEntity processingResult = this.personAuthenticationService.validate(login, password, currentLanguage);

			// analyzing processing result
			if (processingResult.getCode().equals(Code.LOGIN_INFORMATION_SUCCESS)) {

				// updating the status of the changed language
				FacesUtil.getSession().setAttribute(Util.className(SessionController.class.getName()), false);

				// calling SITIS main form
				FacesUtil.getContext().getExternalContext().redirect(this.applicationConfiguration.getStringValue("xhtml.form.name.activities.web.project"));
			}

			// preparing processing results to presentation in the JSF components
			this.processingResultsList.add(processingResult);

			// preparing the processing result
			this.preparePresentationProcessingResults();

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web
			this.handleGeneralException(ge);

		} catch (Exception e) {
			// handling exception
			this.handleException(e);
		}

		// returning to the login page
		return this.applicationConfiguration.getStringValue("xhtml.form.name.login");
	}

	/**
	 * Disconnects the logged person and call login form.
	 * 
	 * @return String The name of the login form
	 */
	public void logout() {
		try {
			// disconnecting logged person
			this.personAuthenticationService.disconnectPerson();

			// calling login form
			FacesUtil.getContext().getExternalContext().redirect(this.applicationConfiguration.getStringValue("xhtml.form.name.login"));

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web.
			this.handleGeneralException(ge);

		} catch (Exception e) {
			// handling exception
			this.handleException(e);
		}
	}

	/**
	 * Accesses the attribute named 'login' and returns its value.
	 * 
	 * @return String - The value of login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the value of attribute named 'login'.
	 * 
	 * @param login
	 *            The name of person login.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Accesses the attribute named 'password' and returns its value.
	 * 
	 * @return String - The password of the logged person.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value of attribute named 'password'.
	 * 
	 * @param password
	 *            The password of logged person.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
