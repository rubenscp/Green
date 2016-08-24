package br.edu.green.web.exception;

import br.edu.green.web.entity.ProcessingResultEntity;

import com.google.gson.Gson;

/**
 * <p>
 * <b> Generic custom exception class to handle the exceptions of the Sitis Web.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 11/11//2015 (creation date)
 */
public class GeneralException extends Exception {

	private static final long serialVersionUID = -87275419812518423L;

	// class attributes
	private ProcessingResultEntity processingResult;
	private String className;
	private String methodName;
	private String exceptionOriginalMessage;
	private String login;
	private String personName;

	/**
	 * Default class constructor.
	 */
	public GeneralException() {
		super();
		this.processingResult = new ProcessingResultEntity();
		this.className = "";
		this.methodName = "";
		this.exceptionOriginalMessage = "";
		this.login = "";
		this.personName = "";
	}

	/**
	 * Class constructor with code error message parameter.
	 * 
	 * @param processingResultCode
	 *            The processing result in the exception
	 */
	public GeneralException(ProcessingResultEntity processingResultCode) {
		this();
		this.processingResult = processingResultCode;
	}

	/**
	 * Class constructor with all exception generic class attributes.
	 * 
	 * @param processingResultCode
	 *            Code of the error message
	 * @param processingResultMessage
	 *            Error message
	 * @param className
	 *            Name of the class
	 * @param methodName
	 *            Name of the method
	 * @param exceptionOriginalMessage
	 *            Error message of original exception
	 */
	public GeneralException(ProcessingResultEntity processingResultCode, String processingResultMessage, String className, String methodName,
			String exceptionOriginalMessage, String login) {
		this();
		this.processingResult = processingResultCode;
		// this.processingResultMessage = processingResultMessage;
		this.className = className;
		this.methodName = methodName;
		this.exceptionOriginalMessage = exceptionOriginalMessage;
		this.exceptionOriginalMessage = login;
	}

	/**
	 * Returns the processing result object.
	 * 
	 * @return ProcessingResult - The processing result object
	 */
	public ProcessingResultEntity getProcessingResult() {
		return processingResult;
	}

	/**
	 * Sets the processing result.
	 * 
	 * @param processingResult
	 *            The processing result object.
	 */
	public void setProcessingResult(ProcessingResultEntity processingResult) {
		this.processingResult = processingResult;
	}

	/**
	 * Returns the class name where the exception occurred.
	 * 
	 * @return String - The class name.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Sets the class name where the exception occurred.
	 * 
	 * @param className
	 *            The class name.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Returns the method name where the exception occurred.
	 * 
	 * @return String - The method name.
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Sets the method name where the exception occurred.
	 * 
	 * @param methodName
	 *            The method name.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Returns the message of the original exception.
	 * 
	 * @return String - The message of the original exception.
	 */
	public String getExceptionOriginalMessage() {
		return exceptionOriginalMessage;
	}

	/**
	 * Sets the message of the original exception.
	 * 
	 * @param exceptionOriginalMessage
	 *            The message of the original exception.
	 */
	public void setExceptionOriginalMessage(String exceptionOriginalMessage) {
		this.exceptionOriginalMessage = exceptionOriginalMessage;
	}

	/**
	 * Returns the login of logged person.
	 * 
	 * @return String - The login of logged person.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login of logged person.
	 * 
	 * @param login
	 *            The login of logged person.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Returns the name of logged person.
	 * 
	 * @return String - The name of logged person.
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * Sets the name of logged person.
	 * 
	 * @param personName
	 *            The name of logged person.
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * Returns the full exception message with its all attributes in the JSON format.
	 * 
	 * @return String - Full exception message with its all attributes in the JSON format.
	 */
	@Override
	public String toString() {
		// obtaining all attributes of the class in the JSON format and returning
		return this.getClass().getSimpleName() + ": { " + this.toJSON() + " }";
	}

	/**
	 * Returns a string with all attributes of this class in the JSON format.
	 * 
	 * @return String - The string with all attributes of this class in the JSON format.
	 */
	public String toJSON() {
		try {
			// formatting the class attributes in the JSON format
			Gson gson = new Gson();
			return gson.toJson(this);
		} catch (Exception e) {
			return null;
		}
	}
}
