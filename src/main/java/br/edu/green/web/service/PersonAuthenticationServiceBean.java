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
	 * @param login
	 *            Person login to validate
	 * @param password
	 *            Person password to validate
	 * 
	 * @return boolean - The boolean result of validation.
	 * @throws GeneralException
	 *             The general exception object
	 */
	@Override
	public ProcessingResultEntity validate(String login, String password, String language) throws GeneralException {

		// defining processing result
		ProcessingResultEntity processingResult = new ProcessingResultEntity();

		// removing spaces of the string
		login = login.trim();

		password = password.trim();

		// validating attributes contents
		if (login.isEmpty()) {
			processingResult.setCodeMessage(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_NULL, this.applicationMessage.getMessage(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_NULL.name()));
			return processingResult;
		}

		if (password.isEmpty()) {
			processingResult.setCodeMessage(Code.LOGIN_ERROR_PASSWORD_NULL, this.applicationMessage.getMessage(Code.LOGIN_ERROR_PASSWORD_NULL.name()));
			return processingResult;
		}

		// try {
		// // checking data of the person at Embrapa Security and returns if success. Otherwise, the system must validate the person at the local data base.
		// processingResult = this.validatePersonAtWebServiceEmbrapaSecurity(login, password, language);
		// if (processingResult.getCode().equals(Code.LOGIN_INFORMATION_SUCCESS)) {
		// return processingResult;
		// }
		// } catch (GeneralException ge) {
		// // nothing to do because if occurs an exception or any error, a new authentication will be done at the local data base
		// } catch (Exception e) {
		// // nothing to do because if occurs an exception or any error, a new authentication will be done at the local data base
		// }

		// checking data of the person at local data base and returning the processing result
		return this.validatePersonAtLocalDataBase(login, password, language);
	}

	/**
	 * Validates the person data using the Embrapa security system enabled by DTI. If successful validate, it updates de local data base to future access.
	 * 
	 * @param login
	 *            Person login to validate
	 * @param password
	 *            Person password to validate
	 * @param language
	 *            Current language associated to person
	 * 
	 * @return boolean - The boolean result of validation.
	 * @throws GeneralException
	 *             The general exception object
	 */
	// private ProcessingResultEntity validatePersonAtWebServiceEmbrapaSecurity(String login, String password, String language) throws GeneralException {
	// try {
	//
	// // defining local variables
	// PersonEntity personEntity;
	// ProcessingResultEntity processingResult = new ProcessingResultEntity();
	//
	// // getting user transaction object
	// this.userTransaction = this.ejbContext.getUserTransaction();
	//
	// // starting bean transaction
	// this.userTransaction.begin();
	//
	// // calling web service to validate person at the SCS
	// // processingResult = this.webServiceCorporativeService.authenticatePerson(login, password);
	// processingResult = null;
	//
	// if (!processingResult.getCode().equals(Code.LOGIN_INFORMATION_SUCCESS)) {
	// // failure at validate person
	// return processingResult;
	// }
	//
	// // finding person at the local data base using his/her login
	// // personEntity = this.personService.findByLogin(this.webServiceCorporativeService.getPersonEntity().getLogin());
	// // if (personEntity == null) {
	// // // finding person at the local data base using his/her registration
	// personEntity = this.personService.findByRegistration(this.webServiceCorporativeService.getPersonEntity().getRegistration());
	// // }
	//
	// if (personEntity == null) {
	// // creating new instance of data person entity
	// personEntity = new PersonEntity();
	//
	// // configuring attributes of the new person entity
	// personEntity.setSitisPersonProfileInitials(SitisPersonProfileEnum.USUARIO.getProfile());
	// }
	//
	// // preparing attributes of person entity to save at local data base
	// // SITIS
	// personEntity.setLogin(this.webServiceCorporativeService.getPersonEntity().getLogin());
	// personEntity.setName(this.webServiceCorporativeService.getPersonEntity().getName());
	// personEntity.setRegistration(this.webServiceCorporativeService.getPersonEntity().getRegistration());
	//
	// // encrypting password before update local data base SITIS
	// personEntity.setPassword(Util.generateMD5(password));
	//
	// personEntity.setWorkplaceId(this.webServiceCorporativeService.getPersonEntity().getWorkplaceId());
	// personEntity.setWorkplaceName(this.webServiceCorporativeService.getPersonEntity().getWorkplaceName());
	// personEntity.setWorkplaceInitials(this.webServiceCorporativeService.getPersonEntity().getWorkplaceInitials());
	// personEntity.setEmail(this.webServiceCorporativeService.getPersonEntity().getEmail());
	// personEntity.setGeneralSituationIndicator(this.webServiceCorporativeService.getPersonEntity().getGeneralSituationIndicator());
	// personEntity.setInternalExternalPerson(this.webServiceCorporativeService.getPersonEntity().getInternalExternalPerson());
	// personEntity.setLastUpdateDate(new Date());
	// personEntity.setLocalRemoteLastLogin(LoginTypeEnum.REMOTE.getLoginType());
	// if (personEntity.getLanguage() == null && language != null) {
	// personEntity.setLanguage(language);
	// }
	//
	// // creating or updating person object at the local data base
	// this.personService.save(personEntity);
	//
	// // storing the logged person object in the session object
	// FacesUtil.storeObjectInSession(personEntity);
	//
	// // returning login with success
	// return processingResult;
	//
	// } catch (GeneralException ge) {
	// throw ge;
	//
	// } catch (Exception e) {
	// throw this.handleException(e.getCause().getCause().getCause(), this.getClass().getSimpleName(), "validatePersonAtWebServiceEmbrapaSecurity",
	// Code.EJB_EXCEPTION, this.getClass().getSimpleName());
	//
	// } finally {
	// try {
	// // committing bean transaction
	// this.userTransaction.commit();
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "validatePersonAtWebServiceEmbrapaSecurity", Code.EJB_EXCEPTION,
	// this.getClass().getSimpleName());
	// }
	//
	// }
	// }

	/**
	 * Validates the person data using the SITIS local database.
	 * 
	 * @param corporateAccount
	 *            Person login to validate
	 * @param password
	 *            Person password to validate
	 * @param language
	 *            Current language associated to person
	 * @return boolean - The boolean result of validation.
	 * @throws GeneralException
	 *             The general exception object
	 */
	// private ProcessingResultEntity validatePersonAtLocalDataBase(String corporateAccount, String password, String language) throws GeneralException {
	private ProcessingResultEntity validatePersonAtLocalDataBase(String login, String password, String language) throws GeneralException {
		try {

			// getting user transaction object
			this.userTransaction = this.ejbContext.getUserTransaction();

			// starting bean transaction
			this.userTransaction.begin();

			// obtaining the registration of the person
			long registration = Long.valueOf(login.substring(1).trim());

			// finding person at the local data base
			PersonEntity personEntity = this.personService.findByRegistration(registration);

			if (personEntity == null) {
				// person doesn't exist at the local data base
				return new ProcessingResultEntity(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_INVALID_LOCALLY, this.applicationMessage.getMessage(Code.LOGIN_ERROR_CORPORATE_ACCOUNT_INVALID_LOCALLY.name()));
			}

			// checking password
			String passwordInformedByPerson = Util.generateMD5(password);
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
