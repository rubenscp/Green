package br.edu.green.web.service;

import java.util.List;

import javax.ejb.Local;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.exception.GeneralException;

/**
 * <p>
 * <b> This class defines the Person service which contains all methods that access SITIS Data Base.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015 (creation date)
 */

@Local
public interface PersonService {

	public PersonEntity save(PersonEntity person) throws GeneralException;

	public void delete(PersonEntity person) throws GeneralException;

	// public PersonEntity findById(long id) throws GeneralException;

	// public PersonEntity findByLogin(String login) throws GeneralException;

	public PersonEntity findByRegistration(long registration) throws GeneralException;

	public List<PersonEntity> findAll(String orderBy) throws GeneralException;

	public List<PersonEntity> findAllByName(String name) throws GeneralException;

}
