package br.edu.green.web.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.http.HttpSession;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.Util;

/**
 * <p>
 * <b> This class defines the services used to authenticates a person in the SITIS Web.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015 (creation date)
 */

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PersonAuthenticationServiceBean extends GeneralService implements PersonAuthenticationService {

	// defining serial version
	private static final long serialVersionUID = 3532867696356233081L;

	// defining constants
	public static final String DEFAULT_SITIS_PROFILE = "USR";

	@EJB
	private PersonService personService;

	/**
	 * Default class constructor.
	 * 
	 * @throws PersonAuthenticationServiceException
	 */
	public PersonAuthenticationServiceBean() throws GeneralException {
		super();

		// initializing services
		this.initializeServices();
	}

	/**
	 * Validates the person data using the Embrapa security system enabled by DTI. If successful validate, it updates the local data base to future access.
	 * 
	 * @param userNameOrEMail
	 *            Person login to validate
	 * @param password
	 *            Person password to validate
	 * 
	 * @return boolean - The boolean result of validation.
	 * @throws GeneralException
	 *             The general exception object
	 */
	@Override
	public ProcessingResultEntity validate(String userNameOrEMail, String password, String language) throws GeneralException {

		// defining processing result
		ProcessingResultEntity processingResult = new ProcessingResultEntity();

		// removing spaces of the string
		userNameOrEMail = userNameOrEMail.trim();
		password = password.trim();

		// validating attributes contents
		if (userNameOrEMail.isEmpty()) {
			processingResult.setCodeMessage(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_NULL, this.applicationMessage.getMessage(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_NULL.name()));
			return processingResult;
		}

		if (password.isEmpty()) {
			processingResult.setCodeMessage(Code.LOGIN_ERROR_PASSWORD_NULL, this.applicationMessage.getMessage(Code.LOGIN_ERROR_PASSWORD_NULL.name()));
			return processingResult;
		}

		// checking data of the person at local data base and returning the processing result
		return this.validatePersonAtLocalDataBase(userNameOrEMail, password, language);
	}

	/**
	 * Validates the person data using the SITIS local database.
	 * 
	 * @param userNameOrEMail
	 *            User name or email of the person to validate
	 * @param password
	 *            Password to validate
	 * @param language
	 *            Current language associated to person
	 * @return ProcessingResultEntity - The result of processing
	 * @throws GeneralException
	 *             The general exception object
	 */
	private ProcessingResultEntity validatePersonAtLocalDataBase(String userNameOrEMail, String password, String language) throws GeneralException {
		try {
			PersonEntity personEntity;

			// getting user transaction object
			this.userTransaction = this.ejbContext.getUserTransaction();

			// starting bean transaction
			this.userTransaction.begin();

			// finding person at the local data base using his/her email
			personEntity = this.personService.findByEmail(userNameOrEMail);
			if (personEntity == null) {
				// finding person at the local data base using his/her user name
				personEntity = this.personService.findByUserName(userNameOrEMail);
			}

			if (personEntity == null) {
				// person doesn't exist at the local data base
				return new ProcessingResultEntity(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_INVALID_LOCALLY, this.applicationMessage.getMessage(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_INVALID_LOCALLY.name()));
			}

			// checking password
			// String passwordInformedByPerson = Util.generateMD5(password);
			String passwordInformedByPerson = password;
			if (!passwordInformedByPerson.equals(personEntity.getPassword())) {
				// invalid password
				return new ProcessingResultEntity(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_INVALID_LOCALLY, this.applicationMessage.getMessage(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_INVALID_LOCALLY.name()));
			}

			// updating data person
			personEntity.setLastUpdateDate(new Date());
			if (!language.isEmpty()) {
				personEntity.setLanguage(language);
			}

			// creating or updating person object at the local data base
			this.savePerson(personEntity);

			// storing the logged person object in the session object
			FacesUtil.storeObjectInSession(personEntity);

			// local validate success
			return new ProcessingResultEntity(Code.LOGIN_INFORMATION_SUCCESS, this.applicationMessage.getMessage(Code.LOGIN_INFORMATION_SUCCESS.name()));

		} catch (Exception e) {
			throw this.handleException(e.getCause().getCause().getCause(), this.getClass().getSimpleName(), "validatePersonAtLocalDataBase", Code.EJB_EXCEPTION, this.getClass().getSimpleName());

		} finally {
			try {
				// committing bean transaction
				this.userTransaction.commit();
			} catch (Exception e) {
				// configuring and throwing details of the actual exception
				throw this.handleException(e, this.getClass().getSimpleName(), "validatePersonAtLocalDataBase", Code.EJB_EXCEPTION, this.getClass().getSimpleName());
			}

		}
	}

	/**
	 * Disconnects the logged actually person.
	 * 
	 * @throws GeneralException
	 *             The general exception object
	 */
	@Override
	public void disconnectPerson() throws GeneralException {
		try {
			// removing the logged person object in the attributes of the
			// session object
			HttpSession httpSession = FacesUtil.getSession();
			httpSession.removeAttribute(Util.className(PersonEntity.class.getName()));

		} catch (Exception e) {
			// processing the exception and throwing new general exception
			throw this.handleException(e, this.getClass().getName(), "disconnectPerson", Code.LOGIN_EXCEPTION_FAILURE_DISCONNECT_PERSON);
		}
	}

	/**
	 * Create or update the person data with situation of the login.
	 * 
	 * @param personEntity
	 *            A person entity object to storage at the local data base
	 * @throws GeneralException
	 *             The general exception object
	 */
	private void savePerson(PersonEntity personEntity) throws GeneralException {
		try {
			// creating or updating person object at the local data base
			this.personService.save(personEntity);
		} catch (GeneralException ge) {
			// handling general exception
			this.handleGeneralException(ge);
			// throwing general exception
			throw ge;
		}
	}
}
