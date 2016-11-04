package br.edu.green.web.service;

import java.util.List;

import javax.ejb.Local;

import br.edu.green.web.entity.ExperimentEntity;
import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.validate.ProcessingResultsList;

/**
 * <p>
 * <b>This class defines the Experiment services which contains all operations about experiments.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 23/09/2016 (creation date)
 */

@Local
public interface ExperimentService {
	public ProcessingResultsList save(ExperimentEntity experiment, PersonEntity person) throws GeneralException;

	public ProcessingResultsList delete(ExperimentEntity experiment) throws GeneralException;

	public List<ExperimentEntity> findAll(String firstFieldOrder, String firstFieldDirection) throws GeneralException;

	public List<ExperimentEntity> findExperimentsByOwner(PersonEntity ownerPerson, String firstFieldOrder, String firstFieldDirection) throws GeneralException;
}
