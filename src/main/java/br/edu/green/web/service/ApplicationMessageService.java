package br.edu.green.web.service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.util.FacesUtil;

/**
 * <p>
 * <b> This class provides a full error or warning messages used by all the application classes. It implements the singleton pattern.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 27/08/2015 (creation date)
 */

public class ApplicationMessageService {

	// constants
	public static final String MESSAGES_FILE = "i18n/messages_pt_BR";

	// classes attributes
	private static ApplicationMessageService instance;
	private ResourceBundle messages;

	/***
	 * This public method implements a singleton design pattern
	 * 
	 * @return the MessageSITIS instance
	 * @throws GeneralException
	 *             The exception thrown when occurring any failure.
	 */
	public static ApplicationMessageService getInstanceof() throws GeneralException {

		// verifying instance of the class
		if (ApplicationMessageService.instance == null) {
			ApplicationMessageService.instance = new ApplicationMessageService();
		}
		// returning the unique instance of the class
		return ApplicationMessageService.instance;
	}

	/***
	 * Constructor method
	 * 
	 * @throws GeneralException
	 *             The exception thrown when occurring any failure.
	 */
	private ApplicationMessageService() throws GeneralException {
		super();

		try {
			// obtaining current language
			Locale currentLocal = FacesUtil.getContext().getViewRoot().getLocale();

			// loading current messages file
			this.messages = ResourceBundle.getBundle(ApplicationMessageService.MESSAGES_FILE, currentLocal);

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE, "Falha na inicialização do serviço Application Message durante carga das mensagens do SITIS Web. Favor contatar a administração do sistema."));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("ApplicationMessageService");
			ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message

			// writing in the message log
			System.out.println(ge.getProcessingResult().toString());

			// throws general exception
			throw ge;
		}
	}

	/**
	 * Recover the message related at the id (number) and formats automatically using the arguments. The messages are recovered by according with system current
	 * language.
	 * 
	 * @param id
	 *            The message code to recover the message text
	 * @param args
	 *            Additional parameters to include in the message text
	 * @return String The formatted message associated with the specified id
	 */
	public String getMessage(String id, String... args) {

		String msg = "";

		// retrieving text message corresponding to the message code
		try {
			msg = this.messages.getString(id);
		} catch (Exception e) {
		}

		// formatting message text
		// MessageFormat msgFormatted = new MessageFormat(msg + ". (" + String.format("%-50.50s", id).trim() + ")");
		MessageFormat msgFormatted = new MessageFormat(msg.trim() + ".");
		return msgFormatted.format(args);
	}

	/**
	 * Recover the short message related at the id (number) and formats automatically using the arguments. The messages are recovered by according with system
	 * current language.
	 * 
	 * @param id
	 *            The message code to recover the message text
	 * @param args
	 *            Additional parameters to include in the message text
	 * @return String The formatted message associated with the specified id
	 */
	// public String getShortMessage(String id, String... args) {
	//
	// String msg = "";
	//
	// // retrieving text message corresponding to the message code
	// try {
	// msg = this.messages.getString(id);
	// } catch (Exception e) {
	// }
	//
	// // formatting message text
	// // MessageFormat msgFormatted = new MessageFormat(msg + ". (" + String.format("%-50.50s", id).trim() + ")");
	// MessageFormat msgFormatted = new MessageFormat(msg + ".");
	// return msgFormatted.format(args);
	// }
}
