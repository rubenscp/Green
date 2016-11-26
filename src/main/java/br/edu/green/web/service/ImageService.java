package br.edu.green.web.service;

import javax.ejb.Local;

import br.edu.green.web.entity.ExperimentEntity;
import br.edu.green.web.entity.ImageEntity;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.validate.ProcessingResultsList;

/**
 * <p>
 * <b>This class defines the Image services which contains all operations about images.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 15/11/2016 (creation date)
 */

@Local
public interface ImageService {
	public ProcessingResultsList saveOnFolder(ImageEntity image) throws GeneralException;

	public ProcessingResultsList save(ImageEntity image) throws GeneralException;

	public ProcessingResultsList delete(ImageEntity image) throws GeneralException;

	// public List<ImageEntity> findAll(String firstFieldOrder, String firstFieldDirection) throws GeneralException;
	//
	// public List<ImageEntity> findImagesByOwner(PersonEntity ownerPerson, String firstFieldOrder, String firstFieldDirection) throws GeneralException;
}
