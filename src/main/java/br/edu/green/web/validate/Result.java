package br.edu.green.web.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * MessagesSITIS.java: This class provides a full error or warning messages used by all the system classes. It uses the singleton pattern.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 27/08/2015
 * 
 */

public class Result {

	// class constants
	public static boolean ERROR   = true; 
	public static boolean SUCCESS = false; 
	
	// class attributes
	private static Result instance;
	private List<String> results;
	private boolean error;

	/***
	 * This public method implements a singleton design pattern
	 * 
	 * @param
	 * @return the MessageSITIS instance
	 */
	public static Result getInstanceof() {

		// verifying instance of the class
		if (Result.instance == null) {
			Result.instance = new Result();
		}

		// returning the unique instance of the class
		return Result.instance;
	}

	/***
	 * Constructor method
	 */
	private Result() {
		super();

		// initialing attributes
		this.results = new ArrayList<String>();
		this.setError(error);
	}

	/**
	 * Add error or warning messages.
	 * 
	 * @param messageSITIS
	 * @return
	 */
	public void addMessages(String messageSITIS, boolean typeMessage) {
		this.results.add(messageSITIS);
		this.error = typeMessage;
	}

	/**
	 * Return messages list
	 * 
	 * @param
	 * @return results
	 */
	public List<String> getResults() {
		return results;
	}

	/**
	 * Return Returns true if this list contains no elements.
	 * 
	 * @param
	 * @return true if the list is empty and false otherwise
	 */
	public boolean isEmpty() {
		return (this.results.isEmpty() ? true : false);
	}

	/**
	 * Clear list removing all elements.
	 * 
	 * @param
	 * @return void
	 */
	public void clear() {
		this.results.clear();
		this.setError(false);
	}

	/**
	 * Add results at the component of the Faces Context.
	 * 
	 * @param facesContext
	 * @return void
	 */
	public void addMessagesToFacesMessages(FacesContext facesContext) {

		// covering all list messages and storing the faces context
		ListIterator<String> listResults = this.results.listIterator();
		while (listResults.hasNext()) {

			// obtaining message
			String message = listResults.next();

			// defining level severity of the messages 
			Severity severity = (isError() ? FacesMessage.SEVERITY_ERROR : FacesMessage.SEVERITY_INFO);
			
			// adding message at the component
			facesContext.addMessage("rubens", new FacesMessage(severity, message, message));
		}
	}

	/**
	 * Indicates that exists error messages.
	 * 
	 * @param
	 * @return boolean
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * Sets value of the error indicator
	 * 
	 * @param
	 * @return boolean
	 */
	public void setError(boolean error) {
		this.error = error;
	}

}
