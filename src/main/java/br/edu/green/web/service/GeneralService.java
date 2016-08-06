package br.edu.green.web.service;

import java.io.Serializable;

/**
 * <p>
 * <b> This class has attributes and methods that will be inherited by the
 * another service classes. This class is an superclass.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 08/12/2015 (creation date)
 */

public class GeneralService implements Serializable {

	// defining serial version
	private static final long serialVersionUID = -9091817366049078717L;

	// defining the general objects
	protected ApplicationMessageService applicationMessage;
	protected ProcessingResult processingResult;
	protected LogService log;

	// private SessionController session;

	/**
	 * Default constructor.
	 */
	public GeneralService() {
		super();
	}

	/**
	 * This method creates the instantiates objects (services) that will be used
	 * by all services classes.
	 * 
	 * @return boolean - The boolean result of the instantiated objects
	 *         (services)
	 * @throws GeneralException
	 *             The general exception object
	 */
	protected void initializeServices() throws GeneralException {
		try {
			// initializing services
			this.applicationMessage = ApplicationMessageService.getInstanceof();
			this.processingResult = new ProcessingResult();
			this.log = new LogService(this);

		} catch (Exception e) {

			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResult(
					Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE,
					this.applicationMessage
							.getMessage(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE
									.name())));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("isServicesReady");
			ge.setExceptionOriginalMessage(e.getMessage()); // the original
															// exception message

			try {
				// writing in the message log
				this.log.error(ge.toString());
			} catch (Exception e2) {
				System.out.println(ge.toString() + "\n" + e2.getMessage());
			}

			// throws general exception
			throw ge;
		}
	}

	/**
	 * Handle general exception of the SITIS Web writing log.
	 * 
	 * @param ge
	 *            General exception of the SITIS Web
	 */
	protected void handleGeneralException(GeneralException ge) {
		try {
			// logging processing result
			this.log.error(ge.toString());
		} catch (Exception e) {
			// special logging exception
			System.out.println(ge.toString() + " - " + e.getMessage());
		}
	}

	/**
	 * Handle exception of the system writing log and preparing the JCF
	 * components to show the messages in the web pages.
	 * 
	 * @param e
	 *            Exception of the SITIS Web
	 * @param code
	 *            The code of processing result
	 * @param classname
	 *            The class name where exception occurred
	 * @param methodName
	 *            The method name of the class where exception occurred
	 */
	protected GeneralException handleException(Exception e, String classname,
			String methodName, Code code, String... codeArgs) {

		// configuring and throwing details of the actual exception
		GeneralException ge = new GeneralException();
		ge.setProcessingResult(new ProcessingResult(code,
				this.applicationMessage.getMessage(code.name(), codeArgs)));
		ge.setClassName(classname);
		ge.setMethodName(methodName);
		ge.setExceptionOriginalMessage(e.getMessage()); // the original
														// exception message

		try {
			// logging processing result
			this.log.error(ge.toString());
		} catch (Exception e2) {
			// special logging exception
			System.out.println(ge.toString() + "\n" + e2.getMessage());
		}

		// returning new general exception
		return ge;
	}

	/**
	 * Returns the processing result
	 * 
	 * @return The processing result of authentication of the person
	 */
	public ProcessingResult getProcessingResult() {
		return this.processingResult;
	}
}
