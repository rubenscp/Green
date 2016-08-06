package br.edu.green.web.service;

import java.util.List;

import javax.ejb.Local;

import br.edu.green.web.entity.PersonEntity;

/**
 * This class defines the Person service which contains all methods that acess SITIS Data Base.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015
 * 
 */

@Local
public interface PersonService {

	public PersonEntity save(PersonEntity person);

	public void delete(PersonEntity person);

	public PersonEntity findById(long id);

	public PersonEntity findByLogin(String login);

	public List<PersonEntity> findAll(String orderBy);

	public List<PersonEntity> findAllByName(String name);

}
