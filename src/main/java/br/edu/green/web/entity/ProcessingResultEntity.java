package br.edu.green.web.entity;

import com.google.gson.Gson;

/**
 * <p>
 * <b>This class represents the result of processing any operation which result can be an information, an error or an exception.</b>
 * </p>
 * <p>
 * The code name of the processing result may contain one of these three key words: 'INFORMATION', 'ERROR' or 'EXCEPTION'. These key words will be used to
 * define the appearance of the message showed in the JSF component.
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015 (creation date)
 */
public class ProcessingResultEntity {

	/**
	 * <p>
	 * <b>Enumerate class that defines the possible processing results.</b>
	 * </p>
	 * 
	 * @author rubens
	 */
	public enum Code {

		// Possible results of the generic operation
		INFORMATION_SUCCESS(0), //
		EXCEPTION(1), //

		// Initialization procedures
		INITIALIZING_SERVICE_INFORMATION_SUCCESS(11), //
		INITIALIZING_SERVICE_EXCEPTION_FAILURE(12), //

		// Possible results in the manipulation of DAO components
		DAO_EXCEPTION_CREATE(20), //
		DAO_EXCEPTION_UPDATE(21), //
		DAO_EXCEPTION_DELETE(22), //
		DAO_EXCEPTION_FIND(23), //

		// Results from manipulation of EJB components
		EJB_EXCEPTION(30), //

		// Possible results in the manipulation of JSF components
		FACES_EXCEPTION_INPUT_OUTPUT(40), //
		FACES_EXCEPTION_STORE_OBJECT_IN_SESSION(41), //
		FACES_EXCEPTION_RECOVERY_OBJECT_IN_SESSION(42), //
		FACES_EXCEPTION_REMOVE_OBJECT_IN_SESSION(43), //

		// Possible results in the general services
		// GENERAL_SERVICE_EXCEPTION_STORE_OBJECT_IN_SESSION(50), //
		// GENERAL_SERVICE_EXCEPTION_RECOVERY_OBJECT_IN_SESSION(51), //

		// Possible results of the authentication person form
		LOGIN_INFORMATION_SUCCESS(100), //
		LOGIN_ERROR_CORPORATE_ACCOUNT_NULL(101), //
		LOGIN_ERROR_PASSWORD_NULL(102), //
		LOGIN_ERROR_CORPORATE_ACCOUNT_PASSWORD_INVALID_EMBRAPA_SECURITY(103), //
		LOGIN_ERROR_CORPORATE_ACCOUNT_INVALID_LOCALLY(104), //
		LOGIN_EXCEPTION_FAILURE_WEB_SERVICE_EMBRAPA_SECURITY(105), //
		LOGIN_EXCEPTION_FAILURE_VALIDATE_PERSON_AT_LOCAL_DATABASE(106), //
		LOGIN_EXCEPTION_FAILURE_UPDATE_LOCAL_DATABASE(107), //
		LOGIN_EXCEPTION_FAILURE_STORAGE_SESSION_HTTP(108), //
		LOGIN_EXCEPTION_FAILURE_DISCONNECT_PERSON(109), //

		// Possible results of the logged person form
		LOGGED_PERSON_INFORMATION_SUCCESS(150), //
		LOGGED_PERSON_ERROR_FAILURE_UPDATE_DATA_PERSON(151), //

		// Possible results of the list of SITIS experiments
		EXPERIMENT_LIST_INFORMATION_SUCCESS(200), //
		EXPERIMENT_LIST_WARNING_EMPTY_LIST(201), //
		EXPERIMENT_LIST_EXCEPTION_ACCESS_DATA_BASE(202), //
		EXPERIMENT_LIST_WARNING_EMPTY_LIST_WITH_PERSON(203), //

		// Possible results of the SITIS experiment form
		EXPERIMENT_INFORMATION_SUCCESS_NEW_EXPERIMENT(200), //
		EXPERIMENT_INFORMATION_SUCCESS_UPDATE_EXPERIMENT(201), //
		EXPERIMENT_INFORMATION_SUCCESS_DELETE_EXPERIMENT(202), //

		// Possible results of SITIS experiment services
		// EXPERIMENT_SERVICE_INFORMATION_SUCCESS(210), //
		// EXPERIMENT_SERVICE_EXCEPTION_FINDALL(211), //

		;

		// Enumerate attribute
		private long code;

		/**
		 * Constructor with one parameter
		 * 
		 * @param code
		 *            The code of processing result of an operation
		 */
		private Code(long code) {
			this.code = code;
		}

		/**
		 * Returns the value of enumerate object
		 * 
		 * @return Value of enumerate object
		 */
		public long code() {
			return this.code;
		}

	}

	// attributes of the class
	private Code code;
	private String message;

	/**
	 * The default constructor.
	 */
	public ProcessingResultEntity() {
		super();
		this.init();
	}

	/**
	 * The constructor with two parameters.
	 * 
	 * @param code
	 *            The processing result code
	 * @param message
	 *            The complete message corresponding to the processing result code
	 */
	public ProcessingResultEntity(Code code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	/**
	 * Returning the processing result code
	 * 
	 * @return Code - The processing result code
	 */
	public Code getCode() {
		return code;
	}

	/**
	 * Setting the complete message corresponding to the processing result code.
	 * 
	 * @return String - The complete message corresponding to the processing result code.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setting the code and the message of the processing result.
	 * 
	 * @param code
	 *            The processing result code
	 * @param message
	 *            The complete message corresponding to the processing result code
	 */
	public void setCodeMessage(Code code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Initializing the class attributes.
	 */
	public void init() {
		this.code = Code.INFORMATION_SUCCESS;
		this.message = "";
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
