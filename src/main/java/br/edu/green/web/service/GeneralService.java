package br.edu.green.web.service;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.transaction.UserTransaction;

import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.validate.ProcessingResultsList;

import com.google.gson.Gson;

/**
 * <p>
 * <b> This class has attributes and methods that will be inherited by the another service classes. This class is an superclass.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @param <T>
 * @since 08/12/2015 (creation date)
 */

public class GeneralService implements Serializable {

	// defining serial version
	private static final long serialVersionUID = -9091817366049078717L;

	// declares a reference to EJB Context
	@Resource
	protected EJBContext ejbContext;

	// declares a user transaction object
	protected UserTransaction userTransaction;

	// defining the general objects
	protected ApplicationMessageService applicationMessage;
	protected LogService log;
	protected ProcessingResultsList processingResultMap;

	/**
	 * Default constructor.
	 */
	public GeneralService() {
		super();
	}

	/**
	 * This method creates the instantiates objects (services) that will be used by all services classes.
	 * 
	 * @return boolean - The boolean result of the instantiated objects (services)
	 * @throws GeneralException
	 *             The general exception object
	 */
	protected void initializeServices() throws GeneralException {
		try {
			// initializing services
			this.applicationMessage = ApplicationMessageService.getInstanceof();
			this.log = new LogService(this);
			this.processingResultMap = new ProcessingResultsList();

		} catch (Exception e) {

			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE, this.applicationMessage.getMessage(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE.name())));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("initializeServices");
			ge.setExceptionOriginalMessage(e.getMessage());

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
		// defining Google JSON object
		Gson gson = new Gson();

		try {
			// logging processing result
			this.log.error(ge.toString());
		} catch (Exception e) {
			// special logging exception
			System.out.println(ge.toString() + " - " + gson.toJson(e));
		}
	}

	/**
	 * Handle exception of the system writing log and preparing the JCF components to show the messages in the web pages.
	 * 
	 * @param t
	 *            Exception of the SITIS Web
	 * @param code
	 *            The code of processing result
	 * @param classname
	 *            The class name where exception occurred
	 * @param methodName
	 *            The method name of the class where exception occurred
	 */
	protected GeneralException handleException(Throwable t, String classname, String methodName, Code code, String... codeArgs) {

		// defining Google JSON object
		// Gson gson = new Gson();

		// configuring and throwing details of the actual exception
		GeneralException ge = new GeneralException();
		ge.setProcessingResult(new ProcessingResultEntity(code, this.applicationMessage.getMessage(code.name(), codeArgs)));
		ge.setClassName(classname);
		ge.setMethodName(methodName);
		ge.setExceptionOriginalMessage(t.getCause().toString());

		try {
			// logging processing result
			this.log.error(ge.toString());
		} catch (Exception e) {
			// special logging exception
			System.out.println(ge.toString() + "\n" + e.toString());
		}

		// returning new general exception
		return ge;
	}
}
