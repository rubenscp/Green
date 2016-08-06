package br.edu.green.web.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * MessagesSITIS.java: This class provides a full error or warning messages used by all the system classes. It uses the singleton pattern.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 27/08/2015
 * 
 */

public class MessagesGreen {

	// constants
	public static final String MESSAGES_FILE = "i18n/messages";

	// classes attributes
	private static MessagesGreen instance;
	private ResourceBundle messages;

	/***
	 * This public method implements a singleton design pattern
	 * 
	 * @return the MessageSITIS instance
	 */
	public static MessagesGreen getInstanceof() {

		// verifying instance of the class
		if (MessagesGreen.instance == null) {
			MessagesGreen.instance = new MessagesGreen();
		}

		// returning the unique instance of the class
		return MessagesGreen.instance;
	}

	/***
	 * Constructor method
	 */
	private MessagesGreen() {
		super();

		// obtaining current language
		Locale currentLocal = FacesUtil.getContext().getViewRoot().getLocale();

		// loading current messages file
		this.messages = ResourceBundle.getBundle(MessagesGreen.MESSAGES_FILE, currentLocal);
	}

	/**
	 * Recover the message related at the id (number) and formats automatically using the arguments. The messages are recovered by according with system current
	 * language.
	 * 
	 * @param id
	 * @param args
	 * @return The formatted message associated with the specified id
	 */
	public String getMensagem(String id, String... args) {

		//MessageFormat msg = new MessageFormat(String.format("%-50.50s", id) + " ; " + this.messages.getString(id));
		MessageFormat msg = new MessageFormat(this.messages.getString(id) + " (" + String.format("%-50.50s", id).trim() + ")");
		return msg.format(args);
	}

}
