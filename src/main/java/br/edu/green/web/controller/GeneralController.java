package br.edu.green.web.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;



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

	// defining the general objects
	protected ApplicationMessageService applicationMessage;
	protected ProcessingResultFormatter processingResultFormatter;
	protected ProcessingResult processingResult;
	protected LabelService labels;
	protected LogService log;
	String login;
	String namePerson;

	// @EJB
	// private SessionController sessionController;

	// private SessionController session;

	/**
	 * Default constructor.
	 */
	public GeneralController() {
		super();
	}

	/**
	 * This method creates the instantiates objects (services) that will be used by all controller classes (managed beans)
	 * 
	 * @return boolean - The boolean result of the instantiated objects (services)
	 * @throws GeneralException
	 *             The general exception object
	 */
	protected void initializeServices() throws GeneralException {
		try {
			// initializing services
			this.applicationMessage = ApplicationMessageService.getInstanceof();
			this.processingResultFormatter = ProcessingResultFormatter.getInstanceof();
			this.processingResultFormatter.clear();
			this.processingResult = new ProcessingResult();
			this.labels = LabelService.getInstanceof();
			this.log = new LogService(this);

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web.
			this.handleGeneralException(ge);

			// throwing general exception
			throw ge;
		}
	}

	/**
	 * Prepares the JCF components to show the messages in the web pages.
	 */
	protected void preparePresentationProcessingResult(ProcessingResult processingResult) {

		try {
			// adding messages to message component of JSF context
			this.processingResultFormatter.addProcessingResult(processingResult);

			// adding messages to faces messages context
			FacesContext facesContext = FacesUtil.getContext();
			processingResultFormatter.addMessagesToFacesMessages(facesContext);

		} catch (GeneralException ge) {
			handleGeneralException(ge);

		} catch (Exception e) {
			handleException(e);
		}
	}

	/**
	 * Handle general exception of the SITIS Web writing log and preparing the JCF components to show the messages in the web pages.
	 * 
	 * @param ge
	 *            General exception of the SITIS Web
	 */
	protected void handleGeneralException(GeneralException ge) {
		try {
			// recovering data login person
			this.recoveryDataLoggedPerson();
			ge.setLogin(this.login);
			ge.setNamePerson(this.namePerson);

			// logging processing result
			this.log.error(ge.toString());

			// clearing messages of processing result
			this.processingResultFormatter.clear();

			// adding messages in the faces message component of the JSF context
			this.processingResultFormatter.addProcessingResult(ge.getProcessingResult());
			FacesContext facesContext = FacesUtil.getContext();
			this.processingResultFormatter.addMessagesToFacesMessages(facesContext);

		} catch (Exception e) {
			// special logging exception
			System.out.println(ge.toString() + " >>>> Segunda exceção: " + e.getMessage());
		}
	}

	/**
	 * Handle exception of the system writing log and preparing the JCF components to show the messages in the web pages.
	 * 
	 * @param e
	 *            Exception of the SITIS Web
	 */
	protected void handleException(Exception e) {
		try {
			// clearing messages of processing result
			this.processingResultFormatter.clear();

			// recovering data login person
			this.recoveryDataLoggedPerson();

			// logging processing result
			this.log.error(e.toString() + " - " + this.login + " " + this.namePerson);

			// loading processing result with suitable messages of exception
			this.processingResult.init();
			this.processingResult.setCodeMessage(Code.EXCEPTION, this.applicationMessage.getMessage(Code.EXCEPTION.name()));

			// adding messages in the faces message component of the JSF context
			this.processingResultFormatter.addProcessingResult(processingResult);
			FacesContext facesContext = FacesUtil.getContext();
			this.processingResultFormatter.addMessagesToFacesMessages(facesContext);

		} catch (Exception e2) {
			// logging exception
			System.out.println(e.toString() + " >>>> Segunda exceção: " + e2.getMessage());
		}
	}

	private void recoveryDataLoggedPerson() {

		// initializing variables
		this.login = "Não logado";
		this.namePerson = "";

		try {
			// restoring objects of the session context
			HttpSession httpSession;
			httpSession = FacesUtil.getSession();
			PersonEntity personEntity = (PersonEntity) httpSession.getAttribute(Util.className(PersonEntity.class.getName()));
			if (personEntity != null) {
				this.login = personEntity.getLogin();
				this.namePerson = personEntity.getName();
			}

		} catch (GeneralException e) {
			// nothing to do
		}
	}
}
