package br.edu.green.web.service;

import java.util.ResourceBundle;

import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;

/**
 * <p>
 * <b> This class provides configuration parameters of SITIS Web. It implements the singleton pattern.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 25/11/2015 (creation date)
 */

public class ApplicationConfigurationService {

	// constants
	public static final String CONFIGURATION_FILE = "cfg/configuration";

	// classes attributes
	private static ApplicationConfigurationService instance;
	private ResourceBundle configuration;

	/***
	 * This public method implements a singleton design pattern
	 * 
	 * @return the ConfigurationService instance
	 * @throws GeneralException
	 *             The exception thrown when occurring any failure.
	 */
	public static ApplicationConfigurationService getInstanceof() throws GeneralException {

		// verifying instance of the class
		if (ApplicationConfigurationService.instance == null) {
			ApplicationConfigurationService.instance = new ApplicationConfigurationService();
		}

		// returning the unique instance of the class
		return ApplicationConfigurationService.instance;
	}

	/***
	 * Default constructor
	 * 
	 * @throws GeneralException
	 *             The exception thrown when occurring any failure.
	 */
	private ApplicationConfigurationService() throws GeneralException {
		super();
		try {
			// loading current messages file
			this.configuration = ResourceBundle.getBundle(ApplicationConfigurationService.CONFIGURATION_FILE);

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE, "Falha na inicialização do serviço Application Configuration Favor contatar a administração do sistema."));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("ApplicationConfigurationService");
			ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message

			// writing in the message log
			System.out.println(ge.getProcessingResult().toString());

			// throws general exception
			throw ge;
		}
	}

	/**
	 * Recover the value corresponding to key.
	 * 
	 * @param key
	 *            The key to recover the corresponding value
	 * @return String The value associated to key
	 */
	public String getStringValue(String key) {

		// defining and initializing work variable
		String value = "";

		// retrieving text message corresponding to the message code
		try {
			value = this.configuration.getString(key).trim();
		} catch (Exception e) {
		}

		// returning value
		return value;
	}

	/**
	 * Recover the int value corresponding to key.
	 * 
	 * @param key
	 *            The key to recover the corresponding value
	 * @return int The int value associated to key
	 */
	public int getIntegerValue(String key) {

		int value = 0;

		// retrieving text message corresponding to the message code
		try {
			value = Integer.valueOf(this.configuration.getString(key).trim());
		} catch (Exception e) {
		}

		// returning value
		return value;
	}
}
