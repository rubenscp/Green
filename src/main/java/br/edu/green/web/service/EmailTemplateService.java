package br.edu.green.web.service;

import javax.ejb.Local;

import br.edu.green.web.entity.EmailTemplateEntity;
import br.edu.green.web.exception.GeneralException;

/**
 * <p>
 * <b> This class defines the Email Template services which contains all methods that access SITIS Data Base.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 17/01/2017 (creation date)
 */

@Local
public interface EmailTemplateService {
	// public ProcessingResultsList save(GreenhouseEntity greenhouse) throws GeneralException;
	//
	// public ProcessingResultsList delete(GreenhouseEntity greenhouse) throws GeneralException;

	public EmailTemplateEntity findByIdentifier(String identifier) throws GeneralException;

	// public List<GreenhouseEntity> findAll(String firstFieldOrder, String firstFieldDirection) throws GeneralException;
}
