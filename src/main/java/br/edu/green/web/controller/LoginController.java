package br.edu.green.web.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.green.web.service.PersonAuthenticationService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.Util;
import br.edu.green.web.validate.Result;

/**
 * LoginController.java: This class validates the user authentication to access the system SITIS Web.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 19/08/2015
 * 
 */

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

	// defining serial version
	private static final long serialVersionUID = 8180609930379821697L;
	
	// defining constants
	private static final String MAIN_PAGE = "main";
	private static final String LOGIN_PAGE = "login";

	// defining the class attributes
	private String login;
	private String password;

	// defining the work objects
	//private SitisWebLog log = SitisWebLog.getInstanceof();
	private Result results = Result.getInstanceof();
	
	@EJB
	private PersonAuthenticationService personAuthenticationService;
	
	/**
	 * This method validates corporate account and its password using the web services of security control system and, when theses web services are disable, it
	 * uses the local data base.
	 * 
	 * @param none
	 * @return String - the page name after validate
	 */
	public String validate() {

		// creating objects
		Result results = Result.getInstanceof();
		String currentLanguage;
		boolean statusChangedLanguage;
		
		// checking changes of the language
		try {
			statusChangedLanguage = (boolean)FacesUtil.getSession().getAttribute(Util.className(LanguageController.class.getName()));
		} catch (Exception e) {
			statusChangedLanguage = false;
		}
		if (statusChangedLanguage) {
			currentLanguage = FacesUtil.getContext().getViewRoot().getLocale().getLanguage();			
		} else {
			currentLanguage = "";
		}
		
		// checking corporate account and password
		this.results = this.personAuthenticationService.validate(login, password, currentLanguage);
		
		// successful login
		if (!this.results.isError()) {
			
			// updating the status of the changed language
			FacesUtil.getSession().setAttribute(Util.className(LanguageController.class.getName()), false);
			
			// returning to main page
			return LoginController.MAIN_PAGE;
		}
		
		// error login
		FacesContext facesContext = FacesUtil.getContext();		
		results.addMessagesToFacesMessages(facesContext);
		return "";
	}

	/**
	 * This method disconnect the logged person and call login form. 
	 * 
	 * @param 
	 * @return 
	 */
	public String logout() {

		// disconnecting logged person 
		//this.personAuthenticationService.disconnectPerson();
		
		// calling login form 
		return LoginController.LOGIN_PAGE;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
