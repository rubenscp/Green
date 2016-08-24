package br.edu.green.web.validate;

import java.util.ArrayList;
import java.util.List;

import br.edu.green.web.entity.ProcessingResultEntity;

/**
 * <p>
 * <b>Implements a list of processing result used by all classes of the system and provides facilities to use it.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 11/03/2016 (creation date)
 */

public class ProcessingResultsList {

	// class attributes
	private List<ProcessingResultEntity> processingResults;
	private boolean errorOrWarningOrException;

	/***
	 * Constructor method
	 */
	public ProcessingResultsList() {
		super();

		// initializing attributes
		this.processingResults = new ArrayList<ProcessingResultEntity>();
		this.clear();
	}

	/**
	 * Return messages list
	 * 
	 * @return results
	 */
	/**
	 * Returns the map of processing results.
	 * 
	 * @return List<ProcessingResultEntity> - The map of processing results.
	 */
	public List<ProcessingResultEntity> getProcessingResults() {
		return processingResults;
	}

	/**
	 * @param processingResults
	 *            the processingResults to set
	 */
	public void setProcessingResults(List<ProcessingResultEntity> processingResults) {
		this.processingResults = processingResults;
	}

	/**
	 * Returns the indicator of error, warning or exception
	 * 
	 * @return boolean - The indicator of error, warning or exception
	 */
	public boolean isErrorOrWarningOrException() {
		return errorOrWarningOrException;
	}

	/**
	 * Sets the indicator of error, warning or exception
	 * 
	 * @param errorOrWarningOrException
	 *            The indicator of error, warning or exception
	 */
	public void setError(boolean errorOrWarningOrException) {
		this.errorOrWarningOrException = errorOrWarningOrException;
	}

	/**
	 * Returns the indication of the map is empty or not.
	 * 
	 * @return boolean - The indication of the map is empty or not.
	 */
	public boolean isEmpty() {
		return (this.processingResults.isEmpty() ? true : false);
	}

	/**
	 * Clear the map.
	 * 
	 */
	public void clear() {
		if (this.processingResults != null) {
			this.processingResults.clear();
			this.errorOrWarningOrException = false;
		}
	}

	/**
	 * Adds one processing result to map.
	 * 
	 * @param processingResultEntity
	 *            The processing result.
	 */
	public void add(ProcessingResultEntity processingResultEntity) {
		// adding processing result to map
		this.processingResults.add(processingResultEntity);

		// evaluates type of processing result to sets error indicator
		if (processingResultEntity.getCode().name().indexOf(ProcessingResultFormatter.ERROR) > -1 || processingResultEntity.getCode().name().indexOf(ProcessingResultFormatter.WARNING) > -1 || processingResultEntity.getCode().name().indexOf(ProcessingResultFormatter.EXCEPTION) > -1) {
			// setting error indicator
			this.errorOrWarningOrException = true;
		}
	}
}
