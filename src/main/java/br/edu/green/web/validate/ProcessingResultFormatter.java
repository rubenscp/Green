package br.edu.green.web.validate;

import javax.faces.application.FacesMessage.Severity;

import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;

/**
 * <p>
 * <b>Provides formatting messages resulting from processing services for JSF components. It implements the singleton pattern.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 27/08/2015 (creation date)
 */

public class ProcessingResultFormatter {

	// class constants
	public static final String INFORMATION = "INFORMATION";
	public static final String WARNING = "WARNING";
	public static final String ERROR = "ERROR";
	public static final String EXCEPTION = "EXCEPTION";

	// class attributes
	private static ProcessingResultFormatter instance;
	// private List<ProcessingResultEntity> processingResults;
	private Severity severity;
	private String severityLabel;

	/***
	 * This public method implements a singleton design pattern
	 * 
	 * @param
	 * @return the MessageGreen instance
	 */
	public static ProcessingResultFormatter getInstanceof() throws GeneralException {

		try {
			// verifying instance of the class
			if (ProcessingResultFormatter.instance == null) {
				ProcessingResultFormatter.instance = new ProcessingResultFormatter();
			}

			// returning the unique instance of the class
			return ProcessingResultFormatter.instance;

		} catch (Exception e) {
			// Creating new instance of general exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE, "Falha na inicialização do serviço ApplicationMessageService"));
			ge.setClassName("ApplicationMessageService");
			ge.setMethodName("getInstanceof");
			ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message
			throw ge;
		}
	}

	/***
	 * Constructor method
	 */
	private ProcessingResultFormatter() {
		super();

		// initialing attributes
		// this.processingResults = new ArrayList<ProcessingResultEntity>();
	}

	// * Plays a message resulting of processing using part of processing result to set the message type.
	// public void addProcessingResult(ProcessingResultEntity processingResult) {
	// this.processingResults.add(processingResult);
	// }

	/**
	 * Return messages list
	 * 
	 * @return results
	 */
	// public List<ProcessingResultEntity> getResults() {
	// return processingResults;
	// }

	/**
	 * Return Returns true if this list contains no elements.
	 * 
	 * @return true if the list is empty and false otherwise
	 */
	// public boolean isEmpty() {
	// return (this.processingResults.isEmpty() ? true : false);
	// }

	/**
	 * Clear list removing all elements.
	 * 
	 * @param
	 */
	// public void clear() {
	// if (this.processingResults != null) {
	// this.processingResults.clear();
	// }
	// }

	/**
	 * Add results at the component of the Faces Context.
	 * 
	 * @param facesContext
	 */
	// public void addMessagesToFacesMessages(FacesContext facesContext, ProcessingResultsList processingResultsList) {
	//
	// // covering all list messages and storing the faces context
	// for (ProcessingResultEntity processingResultEntity : processingResultsList.getProcessingResults()) {
	// // defining severity of processing result
	// this.setSeverity(processingResultEntity);
	//
	// // adding message of processing result at the component
	// facesContext.addMessage(null, new FacesMessage(this.severity, processingResultEntity.getMessage(), null));
	//
	// }
	// }

	// public void addMessagesToFacesMessages(FacesContext facesContext, ProcessingResultsList processingResultsList) {
	//
	// // covering all list messages and storing the faces context
	// ListIterator<ProcessingResultEntity> processingResultsListIterator = this.processingResults.listIterator();
	// while (processingResultsListIterator.hasNext()) {
	//
	// // obtaining result of processing
	// ProcessingResultEntity processingResult = processingResultsListIterator.next();
	//
	// // defining severity of processing result
	// this.setSeverity(processingResult);
	//
	// // adding message of processing result at the component
	// facesContext.addMessage(null, new FacesMessage(this.severity, processingResult.getMessage(), null));
	// }
	// }

	/**
	 * Evaluates the type of processing result to defines the severity level of the message shown to final user.
	 * 
	 * @param processingResult
	 * @return
	 */
	// private void setSeverity(ProcessingResultEntity processingResult) {
	// // evaluates severity of processing result
	// if (processingResult.getCode().name().indexOf(ProcessingResultFormatter.INFORMATION) > -1) {
	// // setting faces message severity at the information level
	// this.severity = FacesMessage.SEVERITY_INFO;
	// this.severityLabel = INFORMATION;
	// } else {
	// if (processingResult.getCode().name().indexOf(ProcessingResultFormatter.ERROR) > -1) {
	// // setting faces message severity at the error level
	// this.severity = FacesMessage.SEVERITY_ERROR;
	// this.severityLabel = ERROR;
	// } else {
	// // setting faces message severity at the exception level
	// this.severity = FacesMessage.SEVERITY_FATAL;
	// this.severityLabel = EXCEPTION;
	// }
	// }
	// }
}
