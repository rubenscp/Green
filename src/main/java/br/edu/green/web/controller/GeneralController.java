package br.edu.green.web.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.green.web.entity.NavigationHistoryEntity;
import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.service.ApplicationConfigurationService;
import br.edu.green.web.service.ApplicationMessageService;
import br.edu.green.web.service.LabelService;
import br.edu.green.web.service.LogService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.Util;
import br.edu.green.web.validate.ProcessingResultFormatter;
import br.edu.green.web.validate.ProcessingResultsList;

/**
 * <p>
 * <b> This class has attributes and methods that will be inherited by the another controller classes (managed beans). This class is an superclass.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 02/12/2015 (creation date)
 */

public class GeneralController implements Serializable {

	// defining serial version
	private static final long serialVersionUID = 5875577253232336147L;

	// defining constants
	public static final String INFORMATION = "INFORMATION";
	public static final String WARNING = "WARNING";
	public static final String ERROR = "ERROR";
	public static final String EXCEPTION = "EXCEPTION";

	public static final boolean LOAD_RELATIONSHIP = true;
	public static final boolean DO_NOT_LOAD_RELATIONSHIP = false;

	public static final boolean RENDERED = true;

	// defining the general objects
	protected ApplicationConfigurationService applicationConfiguration;
	protected ApplicationMessageService applicationMessage;
	protected ProcessingResultsList processingResultsList;
	// protected ProcessingResultFormatter processingResultFormatter;
	protected LabelService labels;
	protected LogService log;
	// protected EmailService emailService;
	String email;
	String namePerson;

	private Severity severity;

	// private String severityLabel;

	/**
	 * Default constructor.
	 */
	public GeneralController() {
		super();
	}

	/**
	 * Creates the instantiates objects (services) that will be used by all controller classes (managed beans)
	 * 
	 * @return boolean - The boolean result of the instantiated objects (services)
	 * @throws GeneralException
	 *             The general exception object
	 */
	protected void initializeServices() throws GeneralException {
		try {
			// initializing services
			this.applicationConfiguration = ApplicationConfigurationService.getInstanceof();
			this.applicationMessage = ApplicationMessageService.getInstanceof();
			this.processingResultsList = new ProcessingResultsList();
			// this.processingResultFormatter = ProcessingResultFormatter.getInstanceof();
			this.labels = LabelService.getInstanceof();
			this.log = new LogService(this);

		} catch (GeneralException ge) {
			// handling general exception of Green
			this.handleGeneralException(ge);

			// throwing general exception
			throw ge;
		}
	}

	// protected void addProcessinfResultMap(ProcessingResultsList processingResultsList) {
	//
	// // adding processing results to messages formatter
	// for (ProcessingResultEntity processingResultEntity : processingResultsList.getProcessingResults()) {
	// // adding one processing result
	// this.preparePresentationProcessingResult(processingResultEntity);
	// }
	// }

	/**
	 * Prepares the JCF components to show the messages in the web pages.
	 * 
	 * @param processingResult
	 *            The processing result object
	 */
	// protected void preparePresentationProcessingResult(ProcessingResultEntity processingResult) {
	//
	// try {
	// // adding messages to message component of JSF context
	// this.processingResultFormatter.addProcessingResult(processingResult);
	//
	// // adding messages to faces messages context
	// FacesContext facesContext = FacesUtil.getContext();
	// processingResultFormatter.addMessagesToFacesMessages(facesContext);
	//
	// } catch (GeneralException ge) {
	// handleGeneralException(ge);
	//
	// } catch (Exception e) {
	// handleException(e);
	// }
	// }
	protected void preparePresentationProcessingResults() {

		try {
			// adding messages to message component of JSF context
			// this.processingResultFormatter.addProcessingResult(processingResult);

			// adding messages to faces messages context
			FacesContext facesContext = FacesUtil.getContext();
			this.addMessagesToFacesMessages(facesContext, this.processingResultsList);

		} catch (GeneralException ge) {
			handleGeneralException(ge);

		} catch (Exception e) {
			handleException(e);
		}
	}

	/**
	 * Handle general exception of the Green writing log and preparing the JCF components to show the messages in the web pages.
	 * 
	 * @param ge
	 *            The general exception of the Green
	 */
	protected void handleGeneralException(GeneralException ge) {
		try {
			// recovering data login person
			this.recoveryDataLoggedPerson();
			ge.setLogin(this.email);
			ge.setPersonName(this.namePerson);

			// logging processing result
			if (this.log != null) {
				this.log.error(ge.toString());
			}

			// clearing messages of processing result
			this.processingResultsList.clear();

			// adding messages in the faces message component of the JSF context
			// this.processingResultFormatter.addProcessingResult(ge.getProcessingResult());
			this.processingResultsList.add(ge.getProcessingResult());
			FacesContext facesContext = FacesUtil.getContext();
			this.addMessagesToFacesMessages(facesContext, this.processingResultsList);

		} catch (Exception e) {
			// special logging exception
			System.out.println(ge.toString() + " >>>> Second exception: " + e.getMessage());
		}
	}

	/**
	 * Handle exception of the system writing log and preparing the JCF components to show the messages in the web pages.
	 * 
	 * @param e
	 *            The exception of the Green
	 */
	protected void handleException(Exception e) {
		try {
			// clearing messages of processing result
			this.processingResultsList.clear();

			// recovering data login person
			this.recoveryDataLoggedPerson();

			// logging processing result
			if (this.log != null) {
				this.log.error(e.toString() + " - " + this.email + " " + this.namePerson);
			}

			// loading processing result with suitable messages of exception
			ProcessingResultEntity processingResultEntity = new ProcessingResultEntity(Code.EXCEPTION, this.applicationMessage.getMessage(Code.EXCEPTION.name()));

			// adding messages in the faces message component of the JSF context
			// this.processingResultFormatter.addProcessingResult(processingResult);
			this.processingResultsList.add(processingResultEntity);
			FacesContext facesContext = FacesUtil.getContext();
			this.addMessagesToFacesMessages(facesContext, this.processingResultsList);

		} catch (Exception e2) {
			// logging exception
			System.out.println(e.toString() + " >>>> Second exception: " + e2.getMessage());
		}
	}

	/**
	 * Recovery the data of the logged person stored in the session.
	 */
	private void recoveryDataLoggedPerson() {

		// initializing variables
		this.email = "Not logged";
		this.namePerson = "";

		try {
			// restoring objects of the session context
			HttpSession httpSession;
			httpSession = FacesUtil.getSession();
			PersonEntity personEntity = (PersonEntity) httpSession.getAttribute(Util.className(PersonEntity.class.getName()));
			if (personEntity != null) {
				this.email = personEntity.getEmail();
				this.namePerson = personEntity.getName();
			}

		} catch (GeneralException e) {
			// nothing to do
		}
	}

	/**
	 * Removes the navigation history menu object from the session context
	 */
	protected void removeNavigationHistoryMenu() {
		try {
			// removing navigation history menu entity
			FacesUtil.recoverObjectInSession(new NavigationHistoryEntity());
		} catch (GeneralException e1) {
			// nothing to do at this moment
		}
	}

	/**
	 * Sets the specific function in the navigation history menu
	 * 
	 * @param function
	 *            The function name to register in the navigation history
	 * @param url
	 *            The URL name of the XHTML page
	 * @param initialize
	 *            Boolean indicator that initializes the navigation history menu
	 */
	protected void setFunctionAtNavigationHistory(String function, String url, boolean initialize) {

		initialize = true;
		function = "";
		url = "";

		// defining navigation history menu entity
		NavigationHistoryEntity navigationHistoryEntity = new NavigationHistoryEntity();

		try {
			// recovering navigation history entity of the session context
			navigationHistoryEntity = FacesUtil.recoverObjectInSession(new NavigationHistoryEntity());
		} catch (GeneralException e1) {
			// nothing to do at this moment
		}

		// evaluating the navigation history object
		if (navigationHistoryEntity == null) {
			// creating a new navigation history
			navigationHistoryEntity = new NavigationHistoryEntity();
		}

		// initializing navigation history
		if (initialize) {
			navigationHistoryEntity.initializeNavigationHistoryMenu();
		}

		// adding specific function
		navigationHistoryEntity.addNavigationHistoryItem(function, url, NavigationHistoryEntity.ENABLE_ITEM_MENU);

		// storing in the session context
		try {
			FacesUtil.storeObjectInSession(navigationHistoryEntity);
		} catch (GeneralException e) {
			// nothing to do
		}
	}

	/**
	 * Initializes the navigation history menu
	 * 
	 */
	protected void initializetNavigationHistory() {

		// defining navigation history menu entity
		NavigationHistoryEntity navigationHistoryEntity = new NavigationHistoryEntity();

		try {
			// recovering navigation history entity of the session context
			navigationHistoryEntity = FacesUtil.recoverObjectInSession(new NavigationHistoryEntity());
		} catch (GeneralException e1) {
			// nothing to do at this moment
		}

		// evaluating the navigation history object
		if (navigationHistoryEntity == null) {
			// creating a new navigation history
			navigationHistoryEntity = new NavigationHistoryEntity();
		}

		// initializing navigation history menu
		navigationHistoryEntity.initializeNavigationHistoryMenu();

		// storing in the session context
		try {
			FacesUtil.storeObjectInSession(navigationHistoryEntity);
		} catch (GeneralException e) {
			// nothing to do
		}
	}

	/**
	 * Add results at the component of the Faces Context.
	 * 
	 * @param facesContext
	 */
	private void addMessagesToFacesMessages(FacesContext facesContext, ProcessingResultsList processingResultsList) {

		// covering all list messages and storing the faces context
		for (ProcessingResultEntity processingResultEntity : processingResultsList.getProcessingResults()) {
			// defining severity of processing result
			this.setSeverity(processingResultEntity);

			// adding message of processing result at the component
			facesContext.addMessage(null, new FacesMessage(this.severity, processingResultEntity.getMessage(), null));

		}
	}

	/**
	 * Evaluates the type of processing result to defines the severity level of the message shown to final user.
	 * 
	 * @param processingResult
	 * @return
	 */
	private void setSeverity(ProcessingResultEntity processingResult) {
		// evaluates severity of processing result
		if (processingResult.getCode().name().indexOf(ProcessingResultFormatter.INFORMATION) > -1) {
			// setting faces message severity at the information level
			this.severity = FacesMessage.SEVERITY_INFO;
		} else {
			if (processingResult.getCode().name().indexOf(ProcessingResultFormatter.WARNING) > -1) {
				// setting faces message severity at the error level
				this.severity = FacesMessage.SEVERITY_WARN;
				// this.severityLabel = ERROR;
			} else {
				if (processingResult.getCode().name().indexOf(ProcessingResultFormatter.ERROR) > -1) {
					// setting faces message severity at the error level
					this.severity = FacesMessage.SEVERITY_ERROR;
					// this.severityLabel = ERROR;
				} else {
					// setting faces message severity at the exception level
					this.severity = FacesMessage.SEVERITY_FATAL;
					// this.severityLabel = EXCEPTION;
				}
			}
		}
	}

	/**
	 * Closes the dialog form.
	 */
	public void closeDialogForm(String dialogFormName) {
		try {
			// showing dialog form to approve the experiment schedule
			FacesUtil.getRequestContext().execute("PF('" + dialogFormName + "').hide();");

		} catch (GeneralException ge) {
			// handling general exception of Green
			this.handleGeneralException(ge);
		}
	}
}
