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
 * <b> This class recover the labels of all visual components of the SITIS Web interface human-machine. It uses the singleton pattern.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 19/08/2015 (creation date)
 */

public class LabelService {

	// constants
	public static final String LABELS_FILE = "i18n/labels_pt_BR";

	// classes attributes
	private static LabelService instance;
	private ResourceBundle labels;

	/***
	 * This public static method implements a singleton design pattern.
	 * 
	 * @return the MessageSITIS instance
	 */
	public static LabelService getInstanceof() throws GeneralException {

		// verifying instance of the class
		if (LabelService.instance == null) {
			LabelService.instance = new LabelService();
		}

		// returning the unique instance of the class
		return LabelService.instance;
	}

	/**
	 * Default constructor
	 * 
	 * @throws GeneralException
	 */
	private LabelService() throws GeneralException {
		super();

		try {
			Locale currentLocal;
			// obtaining current language
			currentLocal = FacesUtil.getContext().getViewRoot().getLocale();
			// loading current messages file
			this.labels = ResourceBundle.getBundle(LabelService.LABELS_FILE, currentLocal);

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE, "Falha na inicialização do serviço Label durante carga dos rótulos do SITIS Web. Favor contatar a administração do sistema."));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("LabelService");
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
	 *            The identification of the message
	 * @param args
	 *            The arguments that will be added at the specified positions of the message
	 * @return String - The formatted message associated with the specified id
	 */
	public String getLabel(String id, String... args) {
		String msg = "";

		// retrieving text message corresponding to the message code
		try {
			msg = this.labels.getString(id);
		} catch (Exception e) {
		}

		// formatting message text
		MessageFormat msgFormatted = new MessageFormat(msg);
		return msgFormatted.format(args);
	}
}
