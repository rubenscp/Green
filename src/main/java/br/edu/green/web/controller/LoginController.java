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
	private static final long serialVersionUID = -1524602280064205321L;

	// defining the class attributes
	private String userNameOrEMail;
	private String password;

	@EJB
	private PersonAuthenticationService personAuthenticationService;

	// @EJB
	// private EmailTemplateService emailTemplateService;

	// ***************************************************************
	// Initializers Methods
	// ***************************************************************

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
	public void initForm() {
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

	// ***************************************************************
	// Getters and Setters Methods
	// ***************************************************************

	/**
	 * Returns the user name or email of the logged person.
	 * 
	 * @return String - The user name or email of the logged person.
	 */
	public String getUserNameOrEMail() {
		return userNameOrEMail;
	}

	/**
	 * Sets the user name or email of the logged person.
	 * 
	 * @param userNameOrEMail
	 *            The user name or email of the logged person.
	 */
	public void setUserNameOrEMail(String userNameOrEMail) {
		this.userNameOrEMail = userNameOrEMail;
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
			ProcessingResultEntity processingResult = this.personAuthenticationService.validate(userNameOrEMail, password, currentLanguage);

			// analyzing processing result
			if (processingResult.getCode().equals(Code.LOGIN_INFORMATION_SUCCESS)) {

				// updating the status of the changed language
				FacesUtil.getSession().setAttribute(Util.className(SessionController.class.getName()), false);

				// recovering logged person and sends email
				// PersonEntity loggedPerson = FacesUtil.recoverObjectInSession(new PersonEntity());
				// this.sendEmail(loggedPerson);

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
	 * Sends email
	 */
	// private void sendEmail(PersonEntity person) {
	// try {
	// // checking environment to send an email
	// if (person.getEmail() == null || person.getEmail() == "") {
	// return;
	// }
	//
	// // getting email template
	// EmailTemplateEntity emailTemplate = this.emailTemplateService.findByIdentifier(EmailTemplateIdentifierEnum.USER_LOGIN.getEmailTemplateIdentifier());
	//
	// // checking email template
	// if (emailTemplate == null) {
	// return;
	// }
	//
	// // getting instance of email formatter
	// EmailFormatter emailFormatter = EmailFormatter.getInstanceof();
	//
	// // preparing list of destination e-mails
	// // List<String> toEmails = new ArrayList<String>(0);
	// emailTemplate.getToEmails().add(person.getEmail());
	//
	// // preparing values used in the body of email
	// Map<String, String> bodyValues = new HashMap<String, String>(0);
	// // bodyValues.put("personRegistration", String.valueOf(person.getRegistration()));
	// bodyValues.put("personName", person.getName());
	// bodyValues.put("currentDate", Util.formatterDate(new Date(), Util.FORMAT_DDMMAAAA_HHMMSS));
	//
	// // preparing email
	// emailTemplate = emailFormatter.format(emailTemplate, null, bodyValues);
	//
	// // sending an email
	// this.emailService.send(emailTemplate);
	//
	// } catch (GeneralException e) {
	// // writing error message
	// this.log.error(this.applicationMessage.getMessage("EMAIL_SERVICE_ERROR_SEND"));
	// }
	// }
}
